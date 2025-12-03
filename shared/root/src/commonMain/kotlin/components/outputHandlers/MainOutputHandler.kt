package components.outputHandlers

import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.replaceAll
import components.RootComponent
import components.RootComponent.Config
import mainFlow.components.MainFlowComponent

fun RootComponent.onMainOutput(
    output: MainFlowComponent.Output
) {
    when (output) {
        is MainFlowComponent.Output.NavigateToItemEditor -> nav.bringToFront(Config.ItemEditor(
            title = output.title,
            category = output.category,
            availableDeliveryTypes = output.availableDeliveryTypes,
            location = output.location,
            requestId = output.requestId
        ))
        MainFlowComponent.Output.NavigateToAuth -> nav.replaceAll(Config.Auth)
        MainFlowComponent.Output.NavigateToRegistration -> nav.replaceAll(Config.Registration)
        MainFlowComponent.Output.NavigateToProfile -> nav.bringToFront(
            Config.ProfileFlow(
                userId = null
            )
        )
    }
}