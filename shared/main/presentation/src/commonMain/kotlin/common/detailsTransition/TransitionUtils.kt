package common.detailsTransition

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import dev.chrisbanes.haze.HazeState
import itemDetails.ui.bottomSheet.SheetValue

internal val LocalTransitionHazeState: ProvidableCompositionLocal<HazeState> =
    staticCompositionLocalOf {
        error("No TransitionHazeState provided")
    }


@Composable
internal fun Transition<SheetValue>.SharedAnimation(
    modifier: Modifier = Modifier,
    content: @Composable AnimatedContentScope.(SheetValue) -> Unit
) {
    this.AnimatedContent(
        transitionSpec = { fadeIn(tween(0)).togetherWith(fadeOut(tween(0))) },
        modifier = modifier
    ) { sheetValue ->
        content(sheetValue)
    }
}