package flow.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.slot.activate
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import flow.components.MainFlowComponent
import itemDetails.ui.ItemDetailsUI

@OptIn(
    ExperimentalDecomposeApi::class, ExperimentalSharedTransitionApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun MainFlowUI(
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

    val hazeState = rememberHazeState()
    val animatedAlpha by animateFloatAsState(if (details != null) .4f else 1f)

    MainFlowContent(
        component = component,
        modifier = Modifier.hazeSource(hazeState).alpha(animatedAlpha),
        topPadding = topPadding,
        bottomPadding = bottomPadding
    )

    details?.also {
        ItemDetailsUI(it, hazeState = hazeState, topPadding = topPadding)
    }
}