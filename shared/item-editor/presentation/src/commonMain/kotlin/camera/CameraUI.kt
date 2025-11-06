package camera

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CameraUI() {
    val cameraPermissionManager = remember { CameraPermissionManager() }

    val cameraCallback = remember {
        CameraCallback {

        }
    }

    if (cameraPermissionManager.isCameraAllowed()) {
        CameraPreview(cameraCallback = cameraCallback)
    } else {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (cameraPermissionManager.isDenied()) {
                Text("Разрешите в настройках!")
            }
            Button(onClick = { cameraPermissionManager.requestCameraPermission() }) {
                Text("Разрешить камеру")
            }
        }
    }
}