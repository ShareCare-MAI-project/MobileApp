package itemDetails.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import common.ItemImage
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import resources.RImages
import view.consts.Paddings

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ItemDetailsContent(
    topPadding: Dp,
    hazeState: HazeState,
    transition: Transition<Boolean>,
    sheet: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .pointerInput(Unit) {} // Prohibit Background scrolling
            .fillMaxSize()
            .padding(top = topPadding)
    ) {
            transition.AnimatedContent(
                transitionSpec = { fadeIn(tween(0)).togetherWith(fadeOut(tween(0))) }
            ) { isDetails ->
                if (isDetails) {
                    ItemImage(
                        path = RImages.LOGO,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(horizontal = Paddings.medium)
                            .fillMaxWidth()
                            .height(350.dp)
                            .hazeSource(hazeState),
                        id = "meow_1",
                        detailedItemId = "meow_1",
                        animatedContentScope = this
                    )
                }
            }
        sheet()
    }
}