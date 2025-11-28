package common.detailsInterfaces

import kotlinx.serialization.Serializable
import logic.enums.DeliveryType
import logic.enums.ItemCategory

@Serializable
sealed interface DetailsConfig {
    @Serializable
    data class ItemDetailsConfig(
        val id: String,
        val images: List<String>,
        val creatorId: String,
        val title: String,
        val description: String,
        val location: String,
        val category: ItemCategory,
        val deliveryTypes: List<DeliveryType>,
        val recipientId: String?,
    ) : DetailsConfig

    @Serializable
    data class RequestDetailsConfig(
        val id: String
    ) : DetailsConfig

}