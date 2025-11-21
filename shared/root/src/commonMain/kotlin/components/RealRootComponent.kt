package components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import components.RootComponent.Child
import components.RootComponent.Config
import components.outputHandlers.onHelloOutput
import components.outputHandlers.onMainOutput
import itemEditorFlow.components.RealItemEditorFlowComponent

class RealRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    override val nav = StackNavigation<Config>()
    private val _stack =
        childStack(
            source = nav,
            serializer = Config.serializer(),
            initialStack = { getInitialStack() },
            childFactory = ::child,
            handleBackButton = true
        )

    override val stack: Value<ChildStack<Config, Child>>
        get() = _stack

    private fun child(config: Config, childContext: ComponentContext): Child {
        return when (config) {
            Config.Hello -> Child.HelloChild(
                 RealHelloComponent(
                    childContext,
                    output = ::onHelloOutput
                )
            )

            Config.MainFlow -> Child.MainFlowChild(
                RealMainFlowComponent(childContext, output = ::onMainOutput)
            )

            Config.ItemEditor -> Child.ItemEditorChild(
                RealItemEditorFlowComponent(
                    childContext,
                    exitFromFlow = { popOnce(Child.ItemEditorChild::class) })
            )
        }
    }

    private fun getInitialStack(): List<Config> {
        return listOf(Config.Hello, Config.MainFlow)
    }
}