package components.outputHandlers

import com.arkivanov.decompose.router.stack.replaceAll
import components.HelloComponent
import components.RootComponent
import components.RootComponent.Config

fun RootComponent.onHelloOutput(
    output: HelloComponent.Output
) {
    when (output) {
        HelloComponent.Output.NavigateToMainFlow -> nav.replaceAll(Config.MainFlow)
    }
}