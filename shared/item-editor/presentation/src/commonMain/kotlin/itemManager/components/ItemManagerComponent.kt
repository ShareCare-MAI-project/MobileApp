package itemManager.components

import photoTaker.components.PhotoTakerComponent

interface ItemManagerComponent {
    val photoTakerComponent: PhotoTakerComponent

    val closeFlow: () -> Unit

    val openPhotoTakerComponent: () -> Unit
}