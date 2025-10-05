package flow.components

import architecture.DefaultStack
import findHelp.components.FindHelpComponent
import flow.components.MainFlowComponent.Child
import flow.components.MainFlowComponent.Config
import kotlinx.serialization.Serializable
import shareCare.components.ShareCareComponent

interface MainFlowComponent : DefaultStack<Config, Child> {

    sealed interface Child {
        data class FindHelpChild(val findHelpComponent: FindHelpComponent) : Child
        data class ShareCareChild(val shareCareComponent: ShareCareComponent) : Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object FindHelp : Config
        @Serializable
        data object ShareCare : Config
    }

    fun navigateTo(cfg: Config)
}