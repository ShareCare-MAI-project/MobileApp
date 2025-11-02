package itemDetails.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.backhandler.PredictiveBackHandler
import androidx.compose.ui.unit.Dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import flow.ui.DetailedItemAnimationManager
import itemDetails.components.ItemDetailsComponent
import itemDetails.ui.bottomSheet.ItemDetailsSheetContent
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
    detailedItemAnimationManager: DetailedItemAnimationManager
) {
    PredictiveBackHandler { progress ->
        try {
            progress.collect { backEvent ->
                try {
                    detailedItemAnimationManager.onBackProgress(backEvent.progress)
                } catch (_: CancellationException) {
                }
            }
            detailedItemAnimationManager.onBackSuccessful()
        } catch (e: CancellationException) {
            detailedItemAnimationManager.onBackFailure()
            throw e
        }
    }

    ItemDetailsContent(
        topPadding = topPadding,
        hazeState = hazeState,
        component = component,
        transition = detailedItemAnimationManager.transition,
        sheet = {
            ItemDetailsSheetContent(
                hazeState = hazeState,
                sharedTransitionScope = this@ItemDetailsUI,
                detailedItemAnimationManager = detailedItemAnimationManager
            )
        }
    )
}