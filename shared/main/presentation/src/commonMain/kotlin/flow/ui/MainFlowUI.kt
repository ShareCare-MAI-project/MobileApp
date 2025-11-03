package flow.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.SeekableTransitionState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.slot.dismiss
import common.LocalTransitionHazeState
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import flow.components.MainFlowComponent
import itemDetails.ui.ItemDetailsUI
import itemDetails.ui.bottomSheet.SheetValue
import itemDetails.ui.bottomSheet.rememberCustomSheetState

@OptIn(
    ExperimentalDecomposeApi::class, ExperimentalSharedTransitionApi::class,
    ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class
)
@Composable
fun SharedTransitionScope.MainFlowUI(
    component: MainFlowComponent
) {

    val safeContentPaddings = WindowInsets.safeContent.asPaddingValues()
    val topPadding = safeContentPaddings.calculateTopPadding()
    val bottomPadding = safeContentPaddings.calculateBottomPadding()

    val detailsSlot by component.detailsSlot.subscribeAsState()

    val details = detailsSlot.child?.instance

    LaunchedEffect(details?.itemId) {
        println("checkit: ${details?.itemId}")
    }

    val hazeState = HazeState()


    val seekableTransitionState = remember(details?.itemId) {
        SeekableTransitionState(SheetValue.Collapsed)
    }
    val transition = rememberTransition(transitionState = seekableTransitionState)

    val coroutineScope = rememberCoroutineScope()

    val sheetHeightPx =
        with(LocalDensity.current) { LocalWindowInfo.current.containerSize.height - 300.dp.toPx() - topPadding.toPx() }
    val sheetHeight = with(LocalDensity.current) { sheetHeightPx.toDp() }
    val sheetState = rememberCustomSheetState(
        heightPx = sheetHeightPx,
        key = details?.itemId
    )

    val detailedItemAnimationManager = remember(details?.itemId) {
        val manager = DetailedItemAnimationManager(
            detailedItemId = details?.itemId,
            transition = transition,
            seekableTransitionState = seekableTransitionState,
            sheetState = sheetState,
            sheetHeight = sheetHeight,
            sheetHeightPx = sheetHeightPx,
            coroutineScope = coroutineScope,
            onBackClicked = {
                // иначе он был уже удалён
                // получаем из detailsSlot, т.к. находимся в remember (старое значение)
                if (details?.itemId == detailsSlot.child?.instance?.itemId) {
                    component.detailsNav.dismiss()
                }
            }
        )
        manager.animateOpen()
        manager
    }


    CompositionLocalProvider(
        LocalTransitionHazeState provides hazeState
    ) {

        MainFlowContent(
            component = component,
            modifier = Modifier.hazeSource(hazeState),
            topPadding = topPadding,
            bottomPadding = bottomPadding,
            detailedItemAnimationManager = detailedItemAnimationManager
        )
        if (details != null) {
            ItemDetailsUI(
                details, hazeState = hazeState, topPadding = topPadding,
                detailedItemAnimationManager = detailedItemAnimationManager
            )
        }

    }

}