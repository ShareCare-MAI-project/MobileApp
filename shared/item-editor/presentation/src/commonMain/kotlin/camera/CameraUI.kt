package camera

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun CameraUI() {
    val cameraPermissionManager = remember { CameraPermissionManager() }

    if (cameraPermissionManager.isCameraAllowed()) {
        CameraPreview()
    } else {
        Button(onClick = {cameraPermissionManager.requestCameraPermission()}) {
            Text("Разрешить камеру")
        }
    }
}