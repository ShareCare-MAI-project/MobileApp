package itemEditor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import itemEditor.components.ItemEditorComponent

@Composable
fun ItemEditorUI(
    component: ItemEditorComponent
) {
    Box(Modifier.fillMaxSize().background(colorScheme.background))

}