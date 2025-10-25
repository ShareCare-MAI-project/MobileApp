package common

import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun ItemCard(
    modifier: Modifier = Modifier,
    title: String
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.largeIncreased
    ) {

    }
}