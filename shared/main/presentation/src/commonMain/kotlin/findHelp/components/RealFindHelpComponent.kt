package findHelp.components

import com.arkivanov.decompose.ComponentContext

class RealFindHelpComponent(
    componentContext: ComponentContext,
    private val openItemDetails: (id: String) -> Unit
) : FindHelpComponent, ComponentContext by componentContext {
    override fun onCardClicked(id: String) {
        openItemDetails(id)
    }
}