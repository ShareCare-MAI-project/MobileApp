package common.itemCard

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import architecture.launchIO
import common.detailsTransition.DetailsAnimator
import common.detailsTransition.SharedAnimation
import itemDetails.ui.bottomSheet.isExpanded
import resources.RImages
import utils.SpacerV
import view.consts.Paddings


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SharedTransitionScope.ItemCard(
    modifier: Modifier = Modifier,
    title: String,
    id: String,
    detailsAnimator: DetailsAnimator,
    onClicked: () -> Unit
) {
    val cardShape = RoundedCornerShape(ItemCardDefaults.cardShapeDp)

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    Card(
        modifier = modifier
            .clip(cardShape)
            .bringIntoViewRequester(bringIntoViewRequester)
            .clickable {
                coroutineScope.launchIO {
                    bringIntoViewRequester.bringIntoView()
                }

                onClicked()
            },
        shape = cardShape
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(ItemCardDefaults.containerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (detailsAnimator.detailedItemId != id) {
                ItemImage(
                    path = RImages.LOGO,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.1f),
                    id = id,
                    detailedItemId = detailsAnimator.detailedItemId,
                    animatedContentScope = null
                )
            } else {
                detailsAnimator.transition.SharedAnimation(
                    modifier = Modifier.fillMaxWidth().aspectRatio(1.1f)
                ) { sheetValue ->
                    if (!sheetValue.isExpanded()) {
                        ItemImage(
                            path = RImages.LOGO,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.1f),
                            id = id,
                            detailedItemId = detailsAnimator.detailedItemId,
                            animatedContentScope = this
                        )
                    }
                }
            }

            SpacerV(Paddings.semiSmall)

            Text(
                title,
                textAlign = TextAlign.Center,
                maxLines = 1,
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
