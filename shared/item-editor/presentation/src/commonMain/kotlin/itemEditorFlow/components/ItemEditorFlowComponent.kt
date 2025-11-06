package itemEditorFlow.components

import architecture.DefaultStack
import kotlinx.serialization.Serializable
import photoTaker.components.PhotoTakerComponent

interface ItemEditorFlowComponent: DefaultStack<ItemEditorFlowComponent.Config, ItemEditorFlowComponent.Child> {

    sealed interface Child {
        data class PhotoTakerChild(val photoTakerComponent: PhotoTakerComponent) : Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object PhotoTaker : Config
    }
}