package itemManager.components

import alertsManager.AlertState
import alertsManager.AlertsManager
import androidx.compose.foundation.text.input.TextFieldState
import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import decompose.componentCoroutineScope
import entities.Item
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import network.NetworkState
import network.NetworkState.AFK.onCoroutineDeath
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import photoTaker.components.PhotoTakerComponent
import usecases.ItemEditorUseCases
import utils.encodeToByteArray

class RealItemManagerComponent(
    componentContext: ComponentContext,
    override val photoTakerComponent: PhotoTakerComponent,
    override val closeFlow: () -> Unit,
    override val openPhotoTakerComponent: () -> Unit,
) : ItemManagerComponent, KoinComponent, ComponentContext by componentContext {
    private val itemEditorUseCases: ItemEditorUseCases = get()

    private val coroutineScope = componentCoroutineScope()
    override val createItemResult: MutableStateFlow<NetworkState<Unit>> =
        MutableStateFlow(NetworkState.AFK)
    override val title = TextFieldState()
    override val description = TextFieldState()


    override val deliveryTypes = MutableStateFlow(listOf<DeliveryType>())


    override val itemCategory = MutableStateFlow<ItemCategory?>(null)


    override fun updateItemCategory(itemCategory: ItemCategory) {
        if (!createItemResult.value.isLoading()) {
            this.itemCategory.value = itemCategory
        }
    }

    override fun updateDeliveryType(deliveryType: DeliveryType) {
        if (!createItemResult.value.isLoading()) {
            if (deliveryType in this.deliveryTypes.value) {
                removeDeliveryType(deliveryType)
            } else {
                addDeliveryType(deliveryType)
            }
        }
    }


    override fun createItem() {
        if (!createItemResult.value.isLoading()) {
            coroutineScope.launchIO {
                val preparedItem: Item = Item(
                    images = photoTakerComponent.pickedPhotos.value.map { it.encodeToByteArray(50) },
                    title = title.text.toString(),
                    description = description.text.toString(),
                    category = itemCategory.value!!,
                    deliveryTypes = deliveryTypes.value,
                    location = "Москва, метро Сокол" // TODO
                )
                itemEditorUseCases.createItem(item = preparedItem).collect {
                    createItemResult.value = it
                }
                withContext(Dispatchers.Main) {
                    createItemResult.value.handle(
                        onError = { AlertsManager.push(AlertState.SnackBar(it.prettyPrint)) }
                    ) {
                        AlertsManager.push(
                            AlertState.SuccessDialog("Предмет создан")
                        )
                        closeFlow()
                    }
                }

            }.invokeOnCompletion {
                createItemResult.value = createItemResult.value.onCoroutineDeath()
            }
        }
    }

    private fun addDeliveryType(deliveryType: DeliveryType) {
        this.deliveryTypes.update { current -> current + deliveryType }
    }

    private fun removeDeliveryType(deliveryType: DeliveryType) {
        this.deliveryTypes.update { current -> current - deliveryType }
    }
}