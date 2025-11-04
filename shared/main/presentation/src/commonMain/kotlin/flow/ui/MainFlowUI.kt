package flow.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.slot.dismiss
import common.detailsTransition.LocalTransitionHazeState
import common.detailsTransition.rememberDetailsAnimator
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import flow.components.MainFlowComponent
import itemDetails.ui.ItemDetailsDefaults
import itemDetails.ui.ItemDetailsUI

@OptIn(
    ExperimentalDecomposeApi::class, ExperimentalSharedTransitionApi::class,
    ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class
)
@Composable
fun SharedTransitionScope.MainFlowUI(
    component: MainFlowComponent
) {
    val density = LocalDensity.current


    val safeContentPaddings = WindowInsets.safeContent.asPaddingValues()

    val topPadding = safeContentPaddings.calculateTopPadding()
    val bottomPadding = safeContentPaddings.calculateBottomPadding()

    val detailsSlot by component.detailsSlot.subscribeAsState()
    val details = detailsSlot.child?.instance


    val hazeState = rememberHazeState() //outHazeState


    val containerSize = LocalWindowInfo.current.containerSize
    val imageHeightPx = remember(containerSize.width) {
        (containerSize.width * ItemDetailsDefaults.aspectRatio).coerceAtMost(containerSize.height * .66f)
    }
    val topPaddingPx = WindowInsets.safeContent.getTop(density)
    val sheetHeightPx =
        remember(
            imageHeightPx,
            topPaddingPx
        ) { containerSize.height - imageHeightPx - topPaddingPx + with(density) { ItemDetailsDefaults.gapHeight.roundToPx() } }


    val detailsAnimator = rememberDetailsAnimator(
        details = details,
        sheetHeightPx = sheetHeightPx,
        imageHeightPx = imageHeightPx
    ) {
        // иначе он был уже удалён
        // получаем из detailsSlot, т.к. находимся в remember (старое значение)
        if (it == detailsSlot.child?.instance?.itemId) {
            component.detailsNav.dismiss()
        }
    }

    CompositionLocalProvider(
        LocalTransitionHazeState provides hazeState
    ) {

        MainFlowContent(
            component = component,
            modifier = Modifier.hazeSource(hazeState),
            topPadding = topPadding,
            bottomPadding = bottomPadding,
            detailsAnimator = detailsAnimator
        )
        if (details != null) {
            ItemDetailsUI(
                details, hazeState = hazeState, topPadding = topPadding,
                detailsAnimator = detailsAnimator
            )
        }

    }

}