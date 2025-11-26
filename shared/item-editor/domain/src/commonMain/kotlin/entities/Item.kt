package entities

import entities.enums.DeliveryType
import entities.enums.ItemCategory

data class Item(
    val images: List<ByteArray>,
    val title: String,
    val description: String,
    val category: ItemCategory,
    val deliveryTypes: List<DeliveryType>,
    val location: String
)
