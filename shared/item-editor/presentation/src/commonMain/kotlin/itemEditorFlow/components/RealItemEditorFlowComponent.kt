package itemEditorFlow.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.backStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import itemEditorFlow.components.ItemEditorFlowComponent.Child
import itemEditorFlow.components.ItemEditorFlowComponent.Config
import photoTaker.components.RealPhotoTakerComponent

class RealItemEditorFlowComponent(
    componentContext: ComponentContext,
    private val exitFromFlow: () -> Unit
) : ItemEditorFlowComponent, ComponentContext by componentContext {

    override val nav  = StackNavigation<Config>()
    private val _stack =
        childStack(
            source = nav,
            serializer = Config.serializer(),
            initialConfiguration = Config.PhotoTaker,
            childFactory = ::child,
            handleBackButton = true
        )

    override val stack: Value<ChildStack<Config, Child>>
        get() = _stack

    private fun child(config: Config, childContext: ComponentContext): Child {
        return when (config) {
            Config.PhotoTaker -> Child.PhotoTakerChild(
                photoTakerComponent = RealPhotoTakerComponent(childContext, onBackClick = {
                    if (stack.backStack.isEmpty()) exitFromFlow()
                    else popOnce(Child.PhotoTakerChild::class)
                })
            )
        }
    }

}