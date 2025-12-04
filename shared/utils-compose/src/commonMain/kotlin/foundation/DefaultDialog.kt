package foundation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun DefaultDialog(
    shape: Shape = shapes.extraLarge,
    dismissable: Boolean = true,
    onDismissRequest: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = dismissable,
            dismissOnClickOutside = dismissable
        )
    ) {
        Surface(Modifier.fillMaxWidth(), shape = shape) {
            content()
        }
    }
}