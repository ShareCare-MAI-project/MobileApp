package itemManager.components

import com.arkivanov.decompose.ComponentContext
import photoTaker.components.PhotoTakerComponent

class RealItemManagerComponent(
    componentContext: ComponentContext,
    override val photoTakerComponent: PhotoTakerComponent,
    override val closeFlow: () -> Unit
) : ComponentContext by componentContext, ItemManagerComponent {
}