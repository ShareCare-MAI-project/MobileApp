package widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import view.consts.Paddings

@Composable
fun SimpleChip(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        style = typography.labelLarge,
        modifier = modifier.border(
            width = 1.dp,
            color = colorScheme.onBackground.copy(alpha = .5f),
            shape = shapes.small
        ).padding(horizontal = Paddings.small),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}