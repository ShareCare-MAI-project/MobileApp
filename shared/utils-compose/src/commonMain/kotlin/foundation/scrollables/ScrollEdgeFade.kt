package foundation.scrollables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi

object ScrollEdgeShadowHeight {
    val small = 80.dp
    val big = 110.dp
}

@Composable
fun BottomScrollEdgeFade(
    modifier: Modifier = Modifier,
    solidHeight: Dp = 0.dp,
    shadowHeight: Dp = ScrollEdgeShadowHeight.small,
    isVisible: Boolean = true
) = ScrollEdgeFade(
    modifier = modifier,
    solidHeight = solidHeight,
    isVisible = isVisible,
    shadowHeight = shadowHeight,
    isReversed = true
)

@OptIn(ExperimentalHazeApi::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun ScrollEdgeFade(
    modifier: Modifier = Modifier,
    solidHeight: Dp = 0.dp,
    shadowHeight: Dp = ScrollEdgeShadowHeight.big,
    isVisible: Boolean = true,
    isReversed: Boolean = false
) {
    val back = colorScheme.background
    val density = LocalDensity.current

    // More compact
    val (solidHeightPx, shadowHeightPx) = with(density) {
        listOf(solidHeight.toPx(), shadowHeight.toPx())
    }


    // TODO: Calibrate this shit

    AnimatedVisibility(
        modifier = modifier, visible = isVisible,
        enter = fadeIn(tween(600)),
        exit = fadeOut(tween(800))
    ) {
        Column(Modifier) {
            Box(
                Modifier.fillMaxWidth().height(solidHeight + shadowHeight)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = with(
                                listOf(
                                    Transparent,
                                    back.copy(alpha = 1f),
                                )
                            ) { if (!isReversed) this.asReversed() else this },
                            startY = solidHeightPx,
                            endY = solidHeightPx + shadowHeightPx
                        )
                    )
            )
        }
    }
}