package utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color

fun Modifier.fastBackground(color: Color) = this.drawBehind {
    drawRect(color)
}