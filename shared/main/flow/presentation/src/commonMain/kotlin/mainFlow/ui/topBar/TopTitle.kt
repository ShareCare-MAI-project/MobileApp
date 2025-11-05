package mainFlow.ui.topBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.grid.ContentType
import common.grid.TransitionHeader
import dev.chrisbanes.haze.HazeState
import utils.SpacerH
import view.consts.Paddings
import widgets.glass.GlassCard

@Composable
internal fun TopTitle(
    modifier: Modifier = Modifier,
    isTitle: Boolean,
    hazeState: HazeState,
    contentType: ContentType?
) {
    AnimatedVisibility(
        isTitle,
        modifier = modifier,
        enter = fadeIn(tween(600)) + expandHorizontally(),
        exit = fadeOut(tween(500)) + shrinkHorizontally(),
    ) {
        Row {
            SpacerH(Paddings.medium)

            GlassCard(
                contentPadding = PaddingValues(
                    vertical = Paddings.semiSmall,
                    horizontal = Paddings.semiMedium
                ),
                hazeState = hazeState,
                isReversedProgressive = true,
                modifier = Modifier.fillMaxHeight()
            ) {
                Box(
                    Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    TransitionHeader(
                        contentType = contentType ?: ContentType.Catalog,
                        isVisible = true
                    )
                }

            }
        }


    }
}