package common.detailsInterfaces

import kotlinx.serialization.Serializable

@Serializable
sealed interface DetailsConfig {
    @Serializable
    data class ItemDetailsConfig(
        val id: String,
        val images: List<String>
    ) : DetailsConfig

    @Serializable
    data class RequestDetailsConfig(
        val id: String
    ) : DetailsConfig

}