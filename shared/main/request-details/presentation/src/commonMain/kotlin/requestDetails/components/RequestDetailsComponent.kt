package requestDetails.components

import androidx.compose.foundation.text.input.TextFieldState
import common.detailsInterfaces.DetailsComponent
import kotlinx.coroutines.flow.StateFlow
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import network.NetworkState

interface RequestDetailsComponent: DetailsComponent {
    val onBackClick: () -> Unit

    val isCreationMode: Boolean

    val requestText: TextFieldState
    val category: StateFlow<ItemCategory?>
    val deliveryTypes: StateFlow<List<DeliveryType>>

    val createRequestResult: StateFlow<NetworkState<Unit>>

    fun updateDeliveryType(deliveryType: DeliveryType)
    fun updateCategory(category: ItemCategory)

    fun createRequest()

}