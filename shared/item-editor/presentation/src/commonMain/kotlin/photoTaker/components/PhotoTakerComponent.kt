package photoTaker.components

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.flow.StateFlow

interface PhotoTakerComponent {
    val onBackClick: () -> Unit


    val pickedPhotos: StateFlow<List<ImageBitmap>>
    fun onPhotoPick(imageBitmap: ImageBitmap)
    fun deletePhoto(imageBitmap: ImageBitmap)
}