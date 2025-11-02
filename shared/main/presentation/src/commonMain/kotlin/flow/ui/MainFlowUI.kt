package flow.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.SeekableTransitionState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.draw.alpha
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.dismiss
import common.LocalTransitionHazeState
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import flow.components.MainFlowComponent
import itemDetails.ui.ItemDetailsUI
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalDecomposeApi::class, ExperimentalSharedTransitionApi::class,
    ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class
)
@Composable
fun SharedTransitionScope.MainFlowUI(
    component: MainFlowComponent
) {
    LaunchedEffect(Unit) { // Tests
        component.detailsNav.activate(MainFlowComponent.DetailsConfig())
    }

    val safeContentPaddings = WindowInsets.safeContent.asPaddingValues()
    val topPadding = safeContentPaddings.calculateTopPadding()
    val bottomPadding = safeContentPaddings.calculateBottomPadding()

    val detailsSlot by component.detailsSlot.subscribeAsState()

    val details = detailsSlot.child?.instance

    val hazeState = HazeState()
    val animatedAlpha = 1f//by animateFloatAsState(if (details != null) .4f else 1f)


    val seekableTransitionState = remember {
        SeekableTransitionState(true)
    }
    val transition = rememberTransition(transitionState = seekableTransitionState)


    val coroutineScope = rememberCoroutineScope()
    fun dismissDetails() {
        coroutineScope.launch {
            seekableTransitionState.animateTo(seekableTransitionState.targetState, tween(1000))
            component.detailsNav.dismiss()
        }
    }

    CompositionLocalProvider(
        LocalTransitionHazeState provides hazeState
    ) {

        MainFlowContent(
            component = component,
            modifier = Modifier.hazeSource(hazeState).alpha(animatedAlpha),
            topPadding = topPadding,
            bottomPadding = bottomPadding,
            detailedItemAnimationInfo = DetailedItemAnimationInfo(
                id = details?.itemId,
                animationProgress = seekableTransitionState.fraction
            ),
            transition = transition
        )
        if (details != null) {
            ItemDetailsUI(
                details, hazeState = hazeState, topPadding = topPadding,
                transition = transition,
                sheetFraction = seekableTransitionState.fraction,
                onDismissRequest = { dismissDetails() },
                seekableTransitionState = seekableTransitionState
            )
        }

    }

}