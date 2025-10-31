package itemDetails.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import itemDetails.components.ItemDetailsComponent

@Composable
fun ItemDetailsUI(
    component: ItemDetailsComponent
) {
    Box(Modifier.fillMaxSize(.5f), contentAlignment = Alignment.Center) {
        Text("fuckoff")
    }
}