package common.detailsInterfaces

import kotlinx.serialization.Serializable
import logic.enums.DeliveryType
import logic.enums.ItemCategory

@Serializable
sealed interface DetailsConfig {

    @Serializable
    data class ItemDetailsConfig(
        val creatorId: String,
        val id: String,
        val images: List<String>,
        val title: String,
        val description: String,
        val location: String,
        val category: ItemCategory,
        val deliveryTypes: List<DeliveryType>,
        val recipientId: String?,
    ) : DetailsConfig

    @Serializable
    data class RequestDetailsConfig(
        val creatorId: String,
        val id: String,
        val text: String,
        val category: ItemCategory?,
        val location: String?,
        val deliveryTypes: List<DeliveryType>,
        val organizationName: String?
    ) : DetailsConfig

}