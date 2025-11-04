package itemDetails.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.backhandler.PredictiveBackHandler
import androidx.compose.ui.unit.Dp
import common.detailsTransition.DetailsAnimator
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import itemDetails.components.ItemDetailsComponent
import itemDetails.ui.bottomSheet.ItemDetailsSheetContent
import itemDetails.ui.detailsContent.ItemDetailsContent
import kotlin.coroutines.cancellation.CancellationException


@OptIn(
    ExperimentalHazeMaterialsApi::class,
    ExperimentalSharedTransitionApi::class, ExperimentalComposeUiApi::class
)//, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ItemDetailsUI(
    component: ItemDetailsComponent,
    hazeState: HazeState,
    topPadding: Dp,
    detailsAnimator: DetailsAnimator
) {
    PredictiveBackHandler { progress ->
        try {
            progress.collect { backEvent ->
                runCatching {
                    detailsAnimator.onBackProgress(backEvent.progress)
                }
            }
            detailsAnimator.onBackSuccessful()
        } catch (e: CancellationException) {
            detailsAnimator.onBackFailure()
            throw e
        }
    }

    ItemDetailsContent(
        component = component,
        topPadding = topPadding,
        hazeState = hazeState,
        detailsAnimator = detailsAnimator,
        sheet = {
            ItemDetailsSheetContent(
                hazeState = hazeState,
                sharedTransitionScope = this@ItemDetailsUI,
                detailsAnimator = detailsAnimator
            )
        }
    )
}