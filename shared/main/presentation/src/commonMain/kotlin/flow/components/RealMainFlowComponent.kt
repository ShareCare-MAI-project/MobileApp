package flow.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import findHelp.components.RealFindHelpComponent
import flow.components.MainFlowComponent.Child
import flow.components.MainFlowComponent.Child.FindHelpChild
import flow.components.MainFlowComponent.Config
import shareCare.components.RealShareCareComponent

class RealMainFlowComponent(
    componentContext: ComponentContext,
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


    private fun child(config: Config, childContext: ComponentContext): Child {
        return when (config) {
            Config.FindHelp -> FindHelpChild(
                RealFindHelpComponent(childContext)
            )

            Config.ShareCare -> Child.ShareCareChild(
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
//        if (activeCfg != initialStack.first()) {
//
//        } else {
//            super.onBackClicked() TODO exit app
//        }
    }


    private fun calculateInitialConfig(): Config {
        return Config.FindHelp
    }

    override fun navigateTo(cfg: Config) {
        nav.bringToFront(cfg)
    }
}