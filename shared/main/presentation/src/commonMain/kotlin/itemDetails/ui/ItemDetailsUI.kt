package itemDetails.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.SeekableTransitionState
import androidx.compose.animation.core.Transition
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.backhandler.PredictiveBackHandler
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import itemDetails.components.ItemDetailsComponent
import itemDetails.ui.bottomSheet.ItemDetailsSheetContent
import itemDetails.ui.bottomSheet.rememberCustomSheetState
import kotlinx.coroutines.launch
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
    transition: Transition<Boolean>,
    sheetFraction: Float,
    seekableTransitionState: SeekableTransitionState<Boolean>,
    onDismissRequest: () -> Unit
) {
    val screenHeight = LocalWindowInfo.current.containerDpSize.height - topPadding


    val sheetHeight = screenHeight - 300.dp

    val sheetHeightPx = with(LocalDensity.current) { sheetHeight.toPx() }

    val sheetState = rememberCustomSheetState(sheetHeight)


    var isInBackGesture by remember { mutableStateOf(false) }


    val coroutineScope = rememberCoroutineScope()

    PredictiveBackHandler { progress ->
        try {
            progress.collect { backEvent ->
                try {
                    isInBackGesture = true
                    seekableTransitionState.seekTo(backEvent.progress, targetState = false)
                } catch (_: CancellationException) {
                }
            }
            seekableTransitionState.animateTo(seekableTransitionState.targetState)
            onDismissRequest()
        } catch (e: CancellationException) {
            seekableTransitionState.snapTo(seekableTransitionState.currentState)
            throw e
        } finally {
            isInBackGesture = false
        }
    }

    ItemDetailsContent(
        topPadding = topPadding,
        hazeState = hazeState,
        transition = transition,
        sheet = {
            ItemDetailsSheetContent(
                sheetState = sheetState,
                hazeState = hazeState,
                sharedTransitionScope = this@ItemDetailsUI,
                sheetHeight = sheetHeight,
                sheetHeightPx = sheetHeightPx,
                backProgress = if (isInBackGesture) sheetFraction else 0f,
                onDrag = {
                    coroutineScope.launch {
                        seekableTransitionState.seekTo(it, false)
                    }
                }
            )
        }
    )
    Text(isInBackGesture.toString())

}