package itemDetails.components

import com.arkivanov.decompose.ComponentContext

class RealItemDetailsComponent(
    componentContext: ComponentContext,
    override val itemId: String = "meow_1"
) : ItemDetailsComponent, ComponentContext by componentContext {
}