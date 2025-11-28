package requestDetails.components

import androidx.compose.foundation.text.input.TextFieldState
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import network.NetworkState
import org.koin.core.component.KoinComponent

class RealRequestDetailsComponent(
    componentContext: ComponentContext,
    override val id: String,
    override val creatorId: String,
    override val currentId: String,

    override val onBackClick: () -> Unit,
) : RequestDetailsComponent, KoinComponent, ComponentContext by componentContext {
    override val isCreationMode: Boolean
        get() = id == "Create" // meow
    override val requestText: TextFieldState = TextFieldState("")
    override val category: MutableStateFlow<ItemCategory?> = MutableStateFlow(null)
    override val deliveryTypes: MutableStateFlow<List<DeliveryType>> = MutableStateFlow(listOf())
    override val createRequestResult: MutableStateFlow<NetworkState<Unit>> =
        MutableStateFlow(NetworkState.AFK)


    // sorry for boilerplate =( (from ItemEditor)
    override fun updateCategory(category: ItemCategory) {
        if (!createRequestResult.value.isLoading()) {
            this.category.value = category
        }
    }

    override fun updateDeliveryType(deliveryType: DeliveryType) {
        if (!createRequestResult.value.isLoading() && isCreationMode) {
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