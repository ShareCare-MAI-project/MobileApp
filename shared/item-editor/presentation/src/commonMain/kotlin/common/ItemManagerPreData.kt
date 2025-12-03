package common

import logic.enums.DeliveryType
import logic.enums.ItemCategory

data class ItemManagerPreData(
    val requestId: String?,
    val title: String,
    val category: ItemCategory?,
    val availableDeliveryTypes: List<DeliveryType>,
    val location: String
)