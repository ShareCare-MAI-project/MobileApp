package components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import findHelp.components.RealFindHelpComponent
import itemDetails.components.ItemDetailsComponent
import itemDetails.components.RealItemDetailsComponent
import mainFlow.components.MainFlowComponent
import mainFlow.components.MainFlowComponent.Child
import mainFlow.components.MainFlowComponent.Child.FindHelpChild
import mainFlow.components.MainFlowComponent.Child.ShareCareChild
import mainFlow.components.MainFlowComponent.Config
import mainFlow.components.MainFlowComponent.DetailsConfig
import mainFlow.components.MainFlowComponent.Output
import shareCare.components.RealShareCareComponent

class RealMainFlowComponent(
    componentContext: ComponentContext,
    override val output: (Output) -> Unit
) : MainFlowComponent, ComponentContext by componentContext {

    override val nav = StackNavigation<Config>()

    val initialConfig = calculateInitialConfig()


    private val _stack =
        childStack(
            source = nav,
            serializer = Config.serializer(),
            initialConfiguration = initialConfig,
            childFactory = ::child,
        )

    override val stack: Value<ChildStack<Config, Child>>
        get() = _stack

    override val detailsNav = SlotNavigation<DetailsConfig>()
    private val _detailsSlot =
        childSlot(
            source = detailsNav,
            serializer = DetailsConfig.serializer(),
            handleBackButton = true,
            childFactory = { configuration, context ->
                RealItemDetailsComponent(context, itemId = configuration.id)
            }

        )


    override val detailsSlot: Value<ChildSlot<*, ItemDetailsComponent>>
        get() = _detailsSlot


    private fun openItemDetails(id: String) {
        detailsNav.activate(DetailsConfig(id = id))
    }

    private fun child(config: Config, childContext: ComponentContext): Child {
        return when (config) {
            Config.FindHelp -> FindHelpChild(
                RealFindHelpComponent(childContext, openItemDetails = ::openItemDetails)
            )

            Config.ShareCare -> ShareCareChild(
                RealShareCareComponent(childContext)
            )
        }
    }

    override fun onBackClicked() {
        val activeCfg = _stack.active.configuration
        nav.bringToFront(
            when (activeCfg) {
                Config.FindHelp -> Config.ShareCare
                Config.ShareCare -> Config.FindHelp
            }
        )
    }
//        if (activeCfg != initialStack.first()) {
//
//        } else {
//            super.onBackClicked() TODO exit app
//        }


    private fun calculateInitialConfig(): Config {
        return Config.FindHelp
    }


    override fun navigateTo(cfg: Config) {
        nav.bringToFront(cfg)
    }
}