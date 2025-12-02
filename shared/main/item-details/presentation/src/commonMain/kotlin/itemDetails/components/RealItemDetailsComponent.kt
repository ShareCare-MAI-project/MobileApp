package itemDetails.components

import alertsManager.AlertState
import alertsManager.AlertsManager
import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import decompose.componentCoroutineScope
import entities.TakeItemResponse
import kotlinx.coroutines.flow.MutableStateFlow
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import network.NetworkState
import network.NetworkState.AFK.onCoroutineDeath
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.ItemDetailsUseCases

class RealItemDetailsComponent(
    componentContext: ComponentContext,
    override val id: String,
    override val currentId: String,
    override val creatorId: String,
    override val images: List<String>,
    override val title: String,
    override val description: String,
    override val location: String,
    override val category: ItemCategory,
    override val deliveryTypes: List<DeliveryType>,
    override val key: String,
    recipientId: String?,
    telegram: String?,
    private val takeItemFromFindHelp: (String) -> Unit,
    private val denyItemFromFlow: () -> Unit,
) : ItemDetailsComponent, KoinComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()
    private val itemDetailsUseCases: ItemDetailsUseCases = get()

    override val isOwner = currentId == creatorId
    override val telegram: MutableStateFlow<String?> = MutableStateFlow(telegram)

    override val recipientId: MutableStateFlow<String?> = MutableStateFlow(recipientId)


    override val takeItemResult: MutableStateFlow<NetworkState<TakeItemResponse>> =
        MutableStateFlow(NetworkState.AFK)
    override val denyItemResult: MutableStateFlow<NetworkState<Unit>> =
        MutableStateFlow(NetworkState.AFK)

    override fun takeItem() {
        if (recipientId.value == null && !isOwner && !takeItemResult.value.isLoading()) {
            coroutineScope.launchIO {
                itemDetailsUseCases.takeItem(id).collect {
                    takeItemResult.value = it
                }
                takeItemResult.value.handle(
                    onError = {
                        AlertsManager.push(AlertState.SnackBar(message = it.prettyPrint))
                    }
                ) {
                    val telegramUsername = it.data.telegram
                    telegram.value = telegramUsername
                    recipientId.value = currentId
                    takeItemFromFindHelp(telegramUsername)
                }
            }.invokeOnCompletion { takeItemResult.value = takeItemResult.value.onCoroutineDeath() }
        }
    }

    override fun denyItem() {
        if (recipientId.value != null && !denyItemResult.value.isLoading()) {
            coroutineScope.launchIO {
                itemDetailsUseCases.denyItem(id).collect {
                    denyItemResult.value = it
                }
                denyItemResult.value.handle(
                    onError = {
                        AlertsManager.push(AlertState.SnackBar(message = it.prettyPrint))
                    }
                ) {
                    recipientId.value = null
                    denyItemFromFlow()
                }
            }
                .invokeOnCompletion { denyItemResult.value = denyItemResult.value.onCoroutineDeath() }
        }
    }
}