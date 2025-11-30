package requestDetails.components

import alertsManager.AlertState
import alertsManager.AlertsManager
import androidx.compose.foundation.text.input.TextFieldState
import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import decompose.componentCoroutineScope
import entities.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import network.NetworkState
import network.NetworkState.AFK.onCoroutineDeath
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.RequestDetailsUseCases

class RealRequestDetailsComponent(
    componentContext: ComponentContext,
    override val id: String,
    override val creatorId: String,
    override val currentId: String,

    override val initialText: String,
    override val initialCategory: ItemCategory?,
    override val initialDeliveryTypes: List<DeliveryType>,


    override val onBackClick: () -> Unit,
) : RequestDetailsComponent, KoinComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()
    private val requestDetailsUseCases: RequestDetailsUseCases = get()

    override val isEditable: Boolean =
        id == "Create" || currentId == creatorId // meow
    override val isCreating: Boolean =
        isEditable && creatorId == ""

    override val requestText: TextFieldState = TextFieldState(initialText)
    override val category: MutableStateFlow<ItemCategory?> = MutableStateFlow(initialCategory)
    override val deliveryTypes: MutableStateFlow<List<DeliveryType>> =
        MutableStateFlow(initialDeliveryTypes)
    override val createRequestResult: MutableStateFlow<NetworkState<Unit>> =
        MutableStateFlow(NetworkState.AFK)


    // sorry for boilerplate =( (from ItemEditor)
    override fun updateCategory(category: ItemCategory) {
        if (!createRequestResult.value.isLoading()) {
            this.category.value = category
        }
    }

    override fun createOrEditRequest() {
        require(isEditable)
        if (isCreating) {
            createRequest()
        }
    }

    private fun createRequest() {
        if (!createRequestResult.value.isLoading()) {
            coroutineScope.launchIO {
                val preparedRequest = Request(
                    text = requestText.text.toString(),
                    category = category.value!!,
                    deliveryTypes = deliveryTypes.value,
                    location = "Москва, метро Сокол" // TODO
                )
                requestDetailsUseCases.createRequest(preparedRequest).collect {
                    createRequestResult.value = it
                }
                withContext(Dispatchers.Main) {
                    createRequestResult.value.handle(
                        onError = { AlertsManager.push(AlertState.SnackBar(it.prettyPrint)) }
                    ) {
                        AlertsManager.push(
                            AlertState.SuccessDialog("Заявка создана")
                        )
                        onBackClick()
                    }
                }

            }.invokeOnCompletion {
                createRequestResult.value = createRequestResult.value.onCoroutineDeath()
            }
        }
    }

    override fun updateDeliveryType(deliveryType: DeliveryType) {
        if (!createRequestResult.value.isLoading() && isEditable) {
            if (deliveryType in this.deliveryTypes.value) {
                removeDeliveryType(deliveryType)
            } else {
                addDeliveryType(deliveryType)
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