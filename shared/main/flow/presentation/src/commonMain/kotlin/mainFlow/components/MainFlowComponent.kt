package mainFlow.components

import architecture.DefaultStack
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.value.Value
import common.detailsInterfaces.DetailsComponent
import common.detailsInterfaces.DetailsConfig
import findHelp.components.FindHelpComponent
import kotlinx.serialization.Serializable
import loading.components.LoadingComponent
import mainFlow.components.MainFlowComponent.Child
import mainFlow.components.MainFlowComponent.Config
import shareCare.components.ShareCareComponent


interface MainFlowComponent : DefaultStack<Config, Child> {




    val loadingComponent: LoadingComponent

    val detailsNav: SlotNavigation<DetailsConfig>
    val detailsSlot: Value<ChildSlot<*, DetailsComponent>>




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

    val output: (Output) -> Unit

    sealed class Output {
        data object NavigateToItemEditor : Output()

        data object NavigateToRegistration : Output()
        data object NavigateToAuth : Output()

        data object NavigateToProfile : Output()
    }
}