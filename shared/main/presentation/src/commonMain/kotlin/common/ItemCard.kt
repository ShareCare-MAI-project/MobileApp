package common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import foundation.AsyncImage
import resources.RImages
import utils.SpacerV
import view.consts.Paddings

@Composable
internal fun ItemCard(
    modifier: Modifier = Modifier,
    title: String
) {

    val containerPadding = Paddings.small

    val cardShapeDp = 20.dp
    val imageShapeDp = cardShapeDp - containerPadding

    val cardShape = RoundedCornerShape(cardShapeDp)
    val imageShape = RoundedCornerShape(imageShapeDp)


    Card(
        modifier = modifier,
        shape = cardShape
    ) {
        Column(
            modifier = Modifier.padding(containerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                path = RImages.LOGO,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(imageShape)
                    .aspectRatio(1.1f),
                contentDescription = null
            )
            SpacerV(Paddings.semiSmall)
            Text(
                title,
                textAlign = TextAlign.Center,
                maxLines = 3,
                style = typography.bodyMediumEmphasized,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Medium
            )
            Text(
                "Москва, метро Славянский бульвар",
                maxLines = 1,
                style = typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.alpha(.7f),
            )
        }
    }
}