package photoTaker.components

import com.arkivanov.decompose.ComponentContext

class RealPhotoTakerComponent(
    componentContext: ComponentContext,
    override val onBackClick: () -> Unit
): ComponentContext by componentContext, PhotoTakerComponent {
}