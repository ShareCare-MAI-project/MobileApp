package itemDetails.components

import com.arkivanov.decompose.ComponentContext
import logic.enums.DeliveryType
import logic.enums.ItemCategory

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
    override val recipientId: String?,
    ) : ItemDetailsComponent, ComponentContext by componentContext {
}