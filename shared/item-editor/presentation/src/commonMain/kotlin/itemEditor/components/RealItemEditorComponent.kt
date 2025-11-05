package itemEditor.components

import com.arkivanov.decompose.ComponentContext

class RealItemEditorComponent(
    componentContext: ComponentContext
) : ItemEditorComponent, ComponentContext by componentContext {
}