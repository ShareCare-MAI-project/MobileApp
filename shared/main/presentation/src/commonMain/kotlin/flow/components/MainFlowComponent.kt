package flow.components

import architecture.DefaultStack
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.value.Value
import findHelp.components.FindHelpComponent
import flow.components.MainFlowComponent.Child
import flow.components.MainFlowComponent.Config
import itemDetails.components.ItemDetailsComponent
import kotlinx.serialization.Serializable
import shareCare.components.ShareCareComponent

interface MainFlowComponent : DefaultStack<Config, Child> {

    val detailsNav: SlotNavigation<DetailsConfig>

    val detailsSlot : Value<ChildSlot<*, ItemDetailsComponent>>

    @Serializable
    data class DetailsConfig(
        val id: String
    )

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