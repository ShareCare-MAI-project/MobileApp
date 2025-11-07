package itemManager.components

import com.arkivanov.decompose.ComponentContext
import photoTaker.components.PhotoTakerComponent

class RealItemManagerComponent(
    componentContext: ComponentContext,
    override val photoTakerComponent: PhotoTakerComponent,
    override val closeFlow: () -> Unit,
    override val openPhotoTakerComponent: () -> Unit
) : ComponentContext by componentContext, ItemManagerComponent {
}