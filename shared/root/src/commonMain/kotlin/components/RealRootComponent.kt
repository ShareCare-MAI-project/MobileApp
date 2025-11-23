package components

import auth.components.RealAuthComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import components.RootComponent.Child
import components.RootComponent.Child.AuthChild
import components.RootComponent.Child.HelloChild
import components.RootComponent.Child.ItemEditorChild
import components.RootComponent.Child.MainFlowChild
import components.RootComponent.Config
import components.outputHandlers.onAuthOutput
import components.outputHandlers.onHelloOutput
import components.outputHandlers.onMainOutput
import itemEditorFlow.components.RealItemEditorFlowComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import registration.components.RealRegistrationComponent
import usecases.AuthUseCases

class RealRootComponent(
    componentContext: ComponentContext
) : RootComponent, KoinComponent, ComponentContext by componentContext {

    override val nav = StackNavigation<Config>()
    private val _stack =
        childStack(
            source = nav,
            serializer = Config.serializer(),
            initialConfiguration = getInitialConfig(),
            childFactory = ::child,
            handleBackButton = true
        )

    override val stack: Value<ChildStack<Config, Child>>
        get() = _stack

    private fun child(config: Config, childContext: ComponentContext): Child {
        return when (config) {
            Config.Hello -> HelloChild(
                RealHelloComponent(
                    childContext,
                    output = ::onHelloOutput
                )
            )

            Config.MainFlow -> MainFlowChild(
                RealMainFlowComponent(childContext, output = ::onMainOutput)
            )

            Config.ItemEditor -> ItemEditorChild(
                RealItemEditorFlowComponent(
                    childContext,
                    exitFromFlow = { popOnce(ItemEditorChild::class) })
            )

            Config.Auth -> AuthChild(
                RealAuthComponent(
                    childContext,
                    output = ::onAuthOutput
                )
            )

            Config.Registration -> Child.RegistrationChild(
                RealRegistrationComponent(childContext) {
                    nav.replaceAll(Config.MainFlow)
                }
            )
        }
    }

    private fun getInitialConfig(): Config {
        val authUseCases: AuthUseCases = get()
        val hasToken = authUseCases.fetchToken() != null
        val hasName = authUseCases.fetchName() != null

        return if (hasToken) {
            if (hasName) Config.MainFlow
            else Config.Registration
        } else Config.Hello
    }
}