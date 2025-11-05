package components

import architecture.DefaultStack
import components.RootComponent.Child
import components.RootComponent.Config
import itemEditor.components.ItemEditorComponent
import kotlinx.serialization.Serializable
import mainFlow.components.MainFlowComponent

interface RootComponent : DefaultStack<Config, Child> {

    sealed interface Child {
        data class HelloChild(val helloComponent: HelloComponent) : Child
        data class MainFlowChild(val mainFlowComponent: MainFlowComponent) : Child

        data class ItemEditorChild(val itemEditorComponent: ItemEditorComponent) : Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object Hello : Config

        @Serializable
        data object MainFlow : Config

        @Serializable
        data object ItemEditor : Config
    }
}