package components.outputHandlers

import com.arkivanov.decompose.router.stack.bringToFront
import components.RootComponent
import components.RootComponent.Config
import mainFlow.components.MainFlowComponent

fun RootComponent.onMainOutput(
    output: MainFlowComponent.Output
) {
    when (output) {
        MainFlowComponent.Output.NavigateToItemEditor -> nav.bringToFront(Config.ItemEditor)
    }
}