package itemDetails.components

import com.arkivanov.decompose.ComponentContext

class RealItemDetailsComponent(
    componentContext: ComponentContext
) : ItemDetailsComponent, ComponentContext by componentContext {
}