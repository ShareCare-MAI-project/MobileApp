package common.itemCard

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import common.detailsTransition.LocalTransitionHazeState
import dev.chrisbanes.haze.hazeSource
import foundation.AsyncImage

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SharedTransitionScope.ItemImage(
    path: String,
    modifier: Modifier,
    id: String?,
    animatedContentScope: AnimatedContentScope?,
    detailedItemId: String?
) {
    val isAnimating = id != null && detailedItemId == id
    val hazeState = if (isAnimating) LocalTransitionHazeState.current else null

    AsyncImage(
        path = path,
        modifier = modifier
            // HAZE
            .then(
                if (animatedContentScope != null && id != null)
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