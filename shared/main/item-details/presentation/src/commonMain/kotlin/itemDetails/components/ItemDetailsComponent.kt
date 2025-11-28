package itemDetails.components

import common.detailsInterfaces.DetailsComponent
import logic.enums.DeliveryType
import logic.enums.ItemCategory

interface ItemDetailsComponent: DetailsComponent {

    val title: String
    val description: String
    val location: String
    val category: ItemCategory
    val deliveryTypes: List<DeliveryType>

    val recipientId: String?
    val images: List<String>


}