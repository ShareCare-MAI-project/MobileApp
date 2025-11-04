package common.detailsTransition

import androidx.compose.animation.core.SeekableTransitionState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalDensity
import itemDetails.components.ItemDetailsComponent
import itemDetails.ui.bottomSheet.SheetValue
import itemDetails.ui.bottomSheet.getCustomSheetState

@Suppress("ComposableNaming")
@Composable
fun rememberDetailsAnimator(
    details: ItemDetailsComponent?,
    imageHeightPx: Float,
    sheetHeightPx: Float,
    onBackClicked: (String?) -> Unit
): DetailsAnimator {
    val density = LocalDensity.current
    val sheetHeight = remember(sheetHeightPx) { with(density) { sheetHeightPx.toDp() } }
    val imageHeight = remember(imageHeightPx) { with(density) { imageHeightPx.toDp() } }


    val detailedItemId = details?.itemId
    val seekableTransitionState = remember(detailedItemId) {
        SeekableTransitionState(SheetValue.Collapsed)
    }
    val transition = rememberTransition(transitionState = seekableTransitionState)
    val coroutineScope = rememberCoroutineScope()


    return remember(detailedItemId) {
        val manager = DetailsAnimator(
            detailedItemId = detailedItemId,
            transition = transition,
            seekableTransitionState = seekableTransitionState,
            sheetState = getCustomSheetState(heightPx = sheetHeightPx),
            sheetHeight = sheetHeight,
            sheetHeightPx = sheetHeightPx,
            coroutineScope = coroutineScope,
            imageHeight = imageHeight,
            onBackClicked = { onBackClicked(detailedItemId) },
            pagerState = PagerState { 3 }
        )
        manager.animateOpen()
        manager
    }
}