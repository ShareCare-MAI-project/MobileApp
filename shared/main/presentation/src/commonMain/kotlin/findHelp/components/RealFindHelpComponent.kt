package findHelp.components

import com.arkivanov.decompose.ComponentContext

class RealFindHelpComponent(
    componentContext: ComponentContext
): FindHelpComponent, ComponentContext by componentContext {
}