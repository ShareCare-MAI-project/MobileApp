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
    var isInitialized = false
        private set

    var isBackGesture = false
        private set
    var isClosing = false
        private set

    fun onBackProgress(progress: Float) {
        coroutineScope.launch {
            isBackGesture = true
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
            isBackGesture = false
            isClosing = true
            val imageAnimation =
                async { seekableTransitionState.animateTo(SheetValue.Collapsed) }
            val sheetAnimation = async { sheetState.animateTo(SheetValue.Collapsed, tween(700)) }
// with await doesn't work
            imageAnimation.join()
            sheetAnimation.join()
            onBackClicked()

        }
    }


    fun onBackFailure() {
        coroutineScope.launch {
            isBackGesture = false
            async { seekableTransitionState.snapTo(SheetValue.Expanded) }
            async { sheetState.animateTo(SheetValue.Expanded) }
        }
    }

    fun animateOpen() {
        coroutineScope.launch {
            val imageAnimation = async { seekableTransitionState.animateTo(SheetValue.Expanded) }
            val sheetAnimation = async { sheetState.animateTo(SheetValue.Expanded) }
            imageAnimation.join()
            sheetAnimation.join()
            isInitialized = true
        }
    }

    fun onSheetDrag(progress: () -> Float) {
        if (isInitialized && !isClosing && !isBackGesture) {
            coroutineScope.launch {
                val p = progress()
                seekableTransitionState.seekTo(p, SheetValue.Collapsed)
                if (p == 1f) {
                    onBackClicked()
                }
            }
        }
    }
}