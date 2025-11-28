package itemDetails.components

import com.arkivanov.decompose.ComponentContext

class RealItemDetailsComponent(
    componentContext: ComponentContext,
    override val id: String,
    override val images: List<String>
) : ItemDetailsComponent, ComponentContext by componentContext {
}