package common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import dev.chrisbanes.haze.hazeSource
import flow.ui.DetailedItemAnimationManager
import foundation.AsyncImage
import itemDetails.ui.bottomSheet.isExpanded
import resources.RImages
import utils.SpacerV
import view.consts.Paddings


object ItemCardDefaults {
    val containerPadding = Paddings.small
    val cardShapeDp = 25.dp
    val imageShapeDp = cardShapeDp - containerPadding

    val imageShape = RoundedCornerShape(imageShapeDp)
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SharedTransitionScope.ItemCard(
    modifier: Modifier = Modifier,
    title: String,
    id: String,
    detailedItemAnimationManager: DetailedItemAnimationManager,
    onClicked: () -> Unit
) {
    val cardShape = RoundedCornerShape(ItemCardDefaults.cardShapeDp)


    Card(
        modifier = modifier
            .clip(cardShape)
            .clickable {
                onClicked()
            },
        shape = cardShape
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(ItemCardDefaults.containerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (detailedItemAnimationManager.detailedItemId != id) {
                ItemImage(
                    path = RImages.LOGO,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.1f),
                    id = id,
                    detailedItemId = detailedItemAnimationManager.detailedItemId,
                    animatedContentScope = null
                )
            } else {
                detailedItemAnimationManager.transition.AnimatedContent(
                    transitionSpec = { fadeIn(tween(0)).togetherWith(fadeOut(tween(0))) },
                    modifier = Modifier.fillMaxWidth().aspectRatio(1.1f)
                ) { sheetValue ->
                    if (!sheetValue.isExpanded()) {
                        ItemImage(
                            path = RImages.LOGO,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.1f),
                            id = id,
                            detailedItemId = detailedItemAnimationManager.detailedItemId,
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SharedTransitionScope.ItemImage(
    path: String,
    modifier: Modifier,
    id: String,
    animatedContentScope: AnimatedContentScope?,
    detailedItemId: String?
) {
    val isAnimating = detailedItemId == id
    val hazeState = if (isAnimating) LocalTransitionHazeState.current else null

    AsyncImage(
        path = path,
        modifier = modifier
            // HAZE
            .then(
                if (animatedContentScope != null)
                    Modifier.sharedElement(
                        rememberSharedContentState(
                            id
                        ),
                        animatedVisibilityScope = animatedContentScope,
                        renderInOverlayDuringTransition = isAnimating

                    )
                else Modifier
            )
            .clip(ItemCardDefaults.imageShape)
            .then(
                if (hazeState != null) Modifier.hazeSource(hazeState)
                else Modifier
            ),
        contentDescription = null // TODO
    )
}
