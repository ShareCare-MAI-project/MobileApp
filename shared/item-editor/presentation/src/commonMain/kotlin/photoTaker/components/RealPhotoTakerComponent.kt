package photoTaker.components

import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RealPhotoTakerComponent(
    componentContext: ComponentContext,
    override val onBackClick: () -> Unit,
): ComponentContext by componentContext, PhotoTakerComponent {


    private val _pickedPhotos = MutableStateFlow<List<ImageBitmap>>(emptyList())
    override val pickedPhotos: StateFlow<List<ImageBitmap>> = _pickedPhotos.asStateFlow()

    override fun onPhotoPick(imageBitmap: ImageBitmap) {
        if (_pickedPhotos.value.size < 5) {
            _pickedPhotos.update { current -> current + imageBitmap }
        }
    }

    override fun deletePhoto(imageBitmap: ImageBitmap) {
        _pickedPhotos.update { current -> current - imageBitmap }
    }
}