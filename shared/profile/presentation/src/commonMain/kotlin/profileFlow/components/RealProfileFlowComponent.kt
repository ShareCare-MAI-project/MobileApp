package profileFlow.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import myProfile.components.RealMyProfileComponent
import org.koin.core.component.KoinComponent
import profileFlow.components.ProfileFlowComponent.Child
import profileFlow.components.ProfileFlowComponent.Config

class RealProfileFlowComponent(
    componentContext: ComponentContext,
    private val userId: String?
) : ProfileFlowComponent, KoinComponent, ComponentContext by componentContext {


    override val nav = StackNavigation<Config>()


    private val _stack =
        childStack(
            source = nav,
            serializer = Config.serializer(),
            initialConfiguration = calculateInitialConfig(),
            childFactory = ::child,
        )

    override val stack: Value<ChildStack<Config, Child>>
        get() = _stack


    private fun child(config: Config, childContext: ComponentContext): Child {
        return when (config) {
            Config.MyProfile -> Child.MyProfileChild(
                RealMyProfileComponent(childContext)
            )
        }
    }


    private fun calculateInitialConfig(): Config {
        return if (userId == null) Config.MyProfile else TODO()
    }
}