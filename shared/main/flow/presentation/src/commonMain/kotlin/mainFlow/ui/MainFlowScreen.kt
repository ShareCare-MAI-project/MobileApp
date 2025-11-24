package mainFlow.ui

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.slot.dismiss
import common.detailsTransition.LocalDetailsAnimator
import common.detailsTransition.LocalTransitionHazeState
import common.detailsTransition.rememberDetailsAnimator
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import itemDetails.ui.ItemDetailsUI
import mainFlow.components.MainFlowComponent

@Composable
fun SharedTransitionScope.MainFlowScreen(
    component: MainFlowComponent
) {
    val detailsSlot by component.detailsSlot.subscribeAsState()
    val details = detailsSlot.child?.instance
    val detailsAnimator = rememberDetailsAnimator(
        detailedItemId = details?.itemId
    ) {
        // иначе он был уже удалён
        // получаем из detailsSlot, т.к. находимся в remember (старое значение)
        if (it == detailsSlot.child?.instance?.itemId) {
            component.detailsNav.dismiss()
        }
    }


    val hazeState = rememberHazeState() //outHazeState
    CompositionLocalProvider(
        LocalTransitionHazeState provides hazeState,
        LocalDetailsAnimator provides detailsAnimator
    ) {

        MainFlowContent(
            component = component,
            modifier = Modifier.hazeSource(hazeState)
        )
        if (details != null) {
            ItemDetailsUI(
                details
            )
        }

    }
}