package itemDetails.components

import com.arkivanov.decompose.ComponentContext

class RealItemDetailsComponent(
    componentContext: ComponentContext,
    override val itemId: String
) : ItemDetailsComponent, ComponentContext by componentContext {
}