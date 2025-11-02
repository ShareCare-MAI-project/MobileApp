package flow.ui

import androidx.compose.animation.core.SeekableTransitionState
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.ui.unit.Dp
import itemDetails.ui.bottomSheet.SheetValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class DetailedItemAnimationManager(
    val detailedItemId: String?,
    val transition: Transition<SheetValue>,
    val seekableTransitionState: SeekableTransitionState<SheetValue>,
    val sheetState: AnchoredDraggableState<SheetValue>,
    val sheetHeight: Dp,
    val sheetHeightPx: Float,
    val onBackClicked: () -> Unit,
    val coroutineScope: CoroutineScope,
) {

    fun onBackProgress(progress: Float) {
        coroutineScope.launch {
            async {
                seekableTransitionState.seekTo(
                    progress,
                    targetState = SheetValue.Collapsed
                )
            }
            async {
                sheetState.anchoredDrag {
                    this.dragTo(progress * sheetHeightPx)
                }
            }
        }
    }

    fun onBackSuccessful() {
        coroutineScope.launch {
            val imageAnimation =
                async { seekableTransitionState.animateTo(SheetValue.Collapsed, tween(700)) }
            val sheetAnimation = async { sheetState.animateTo(SheetValue.Collapsed, tween(700)) }
// with await doesn't work
            imageAnimation.join()
            sheetAnimation.join()
            onBackClicked()
        }
    }


    fun onBackFailure() {
        coroutineScope.launch {
            seekableTransitionState.snapTo(SheetValue.Expanded)
            sheetState.animateTo(SheetValue.Expanded)
        }
    }

    fun onSheetDrag(float: Float) {
        coroutineScope.launch {
            seekableTransitionState.seekTo(float, SheetValue.Collapsed)

            if (float == 1f) {
                onBackClicked()
            }
        }
    }
}