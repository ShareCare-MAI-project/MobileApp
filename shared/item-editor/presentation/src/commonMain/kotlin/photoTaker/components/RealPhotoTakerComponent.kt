package photoTaker.components

import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class RealPhotoTakerComponent(
    componentContext: ComponentContext,
    override val goBack: () -> Unit,
) : ComponentContext by componentContext, PhotoTakerComponent {


    override val pickedPhotos = MutableStateFlow<List<ImageBitmap>>(emptyList())

    override fun onPhotoPick(imageBitmap: ImageBitmap) {
        if (pickedPhotos.value.size < 5) {
            // IMPORTANT: реверсия, т.к. иначе скролл лагает (wtf)
            this.pickedPhotos.update { current -> listOf<ImageBitmap>() + current + imageBitmap }
        }
    }

    override fun deletePhoto(imageBitmap: ImageBitmap) {
        this.pickedPhotos.update { current -> current - imageBitmap }
    }
}