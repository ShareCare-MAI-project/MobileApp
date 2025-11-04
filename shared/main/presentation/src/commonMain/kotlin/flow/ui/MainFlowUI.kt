package flow.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.slot.dismiss
import common.detailsTransition.LocalTransitionHazeState
import common.detailsTransition.rememberDetailsAnimator
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import flow.components.MainFlowComponent
import itemDetails.ui.ItemDetailsUI

@OptIn(
    ExperimentalDecomposeApi::class, ExperimentalSharedTransitionApi::class,
    ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class
)
@Composable
fun SharedTransitionScope.MainFlowUI(
    component: MainFlowComponent
) {
    val detailsSlot by component.detailsSlot.subscribeAsState()
    val details = detailsSlot.child?.instance
    val detailsAnimator = rememberDetailsAnimator(
        details = details
    ) {
        // иначе он был уже удалён
        // получаем из detailsSlot, т.к. находимся в remember (старое значение)
        if (it == detailsSlot.child?.instance?.itemId) {
            component.detailsNav.dismiss()
        }
    }


    val hazeState = rememberHazeState() //outHazeState
    CompositionLocalProvider(
        LocalTransitionHazeState provides hazeState
    ) {

        MainFlowContent(
            component = component,
            modifier = Modifier.hazeSource(hazeState),
            detailsAnimator = detailsAnimator
        )
        if (details != null) {
            ItemDetailsUI(
                details, hazeState = hazeState,
                detailsAnimator = detailsAnimator
            )
        }

    }

}