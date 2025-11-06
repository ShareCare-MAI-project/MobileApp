package photoTaker.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import architecture.launchIO
import camera.CameraCallback
import camera.CameraEvent
import camera.CameraPreview
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import photoTaker.components.PhotoTakerComponent
import view.consts.Paddings
import view.consts.Sizes
import widgets.glass.BackGlassButton
import widgets.glass.GlassCard
import widgets.glass.GlassCardFunctions

@Composable
internal fun CameraUI(
    component: PhotoTakerComponent
) {
    var imageBitmap: ImageBitmap? by remember { mutableStateOf(null) }

    val cameraCallback = remember {
        CameraCallback {
            imageBitmap = it
        }
    }


    val safeContentPaddings = WindowInsets.safeContent.asPaddingValues()
    val topPadding = safeContentPaddings.calculateTopPadding()
    val bottomPadding = safeContentPaddings.calculateBottomPadding()

    val bottomBarPadding =
        (if (bottomPadding > Paddings.semiLarge) bottomPadding
        else Paddings.semiLarge) + Paddings.huge


    val hazeState = rememberHazeState()

    val coroutineScope = rememberCoroutineScope()

    Box() {
        Box(Modifier.hazeSource(hazeState)) {
            CameraPreview(
                modifier = Modifier.fillMaxSize(),
                cameraCallback = cameraCallback
            )
        }
        Box(Modifier.padding(top = topPadding + Paddings.small, start = Paddings.medium)) {
            BackGlassButton(modifier = Modifier, hazeState = hazeState) {
                component.onBackClick()
            }
        }

        Row(Modifier.align(Alignment.BottomCenter).padding(bottom = bottomBarPadding)) {
            GlassCard(
                modifier = Modifier.size(Sizes.hugeCircularButton).clickable {
                    coroutineScope.launchIO {
                        cameraCallback.sendEvent(CameraEvent.CaptureImage)
                    }
                },
                hazeState = hazeState,
                shape = CircleShape,
                hazeTint = GlassCardFunctions.getHazeTintColor(
                    tint = null,
                    containerColor = colorScheme.primaryContainer,
                    containerColorAlpha = .7f
                ),
                contentColor = Color.White,
            ) {
                Icon(
                    Icons.Rounded.Camera, null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    if (imageBitmap != null) {
        Image(bitmap = imageBitmap!!, modifier = Modifier.fillMaxSize(), contentDescription = null)

    }
}