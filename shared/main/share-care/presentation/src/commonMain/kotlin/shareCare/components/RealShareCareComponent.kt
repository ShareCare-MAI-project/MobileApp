package shareCare.components

import com.arkivanov.decompose.ComponentContext

class RealShareCareComponent(
    componentContext: ComponentContext
): ShareCareComponent, ComponentContext by componentContext {
}