package itemDetails.components

import common.detailsInterfaces.DetailsComponent
import entities.TakeItemResponse
import kotlinx.coroutines.flow.StateFlow
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import network.NetworkState

interface ItemDetailsComponent: DetailsComponent {

    val title: String
    val description: String
    val location: String
    val category: ItemCategory
    val deliveryTypes: List<DeliveryType>

    val images: List<String>


    val recipientId: StateFlow<String?>


    val isOwner: Boolean

    val telegram: StateFlow<String?>


    val takeItemResult: StateFlow<NetworkState<TakeItemResponse>>

    fun takeItem()

}