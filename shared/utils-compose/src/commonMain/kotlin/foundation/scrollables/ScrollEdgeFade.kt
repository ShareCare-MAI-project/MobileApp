package foundation.scrollables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.HazeInputScale
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials

object ScrollEdgeProgressiveHeight {
    val big = 90.dp
}

object ScrollEdgeShadowHeight {
    val small = 80.dp
    val big = 110.dp
}

@Composable
fun BottomScrollEdgeFade(
    modifier: Modifier = Modifier,
    solidHeight: Dp = 0.dp,
    isVisible: Boolean = true,
) = ScrollEdgeFade(
    modifier = modifier,
    solidHeight = solidHeight,
    isVisible = isVisible,
    shadowHeight = ScrollEdgeShadowHeight.small,
    hazeState = null,
    isReversed = true
)

@OptIn(ExperimentalHazeApi::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun ScrollEdgeFade(
    modifier: Modifier = Modifier,
    solidHeight: Dp = 0.dp,
    progressiveHeight: Dp = ScrollEdgeProgressiveHeight.big,
    shadowHeight: Dp = ScrollEdgeShadowHeight.big,
    isVisible: Boolean = true,
    isReversed: Boolean = false,
    hazeState: HazeState?,
) {
    val back = colorScheme.background
    val density = LocalDensity.current

    // More compact
    val (solidHeightPx, progressiveHeightPx, shadowHeightPx) = with(density) {
        listOf(solidHeight.toPx(), progressiveHeight.toPx(), shadowHeight.toPx())
    }


    AnimatedVisibility(
        modifier = modifier, visible = isVisible,
        enter = fadeIn(tween(600)),
        exit = fadeOut(tween(800))
    ) {
        Column(Modifier) {
            Box(
                Modifier.fillMaxWidth().height(solidHeight + max(shadowHeight, progressiveHeight))
                    .then(
                        if (hazeState != null) {
                            Modifier.hazeEffect(
                                hazeState, HazeMaterials.thick(colorScheme.background)
                            ) {
                                this.noiseFactor = 0f
                                this.inputScale = HazeInputScale.Fixed(1f)

                                progressive = HazeProgressive.verticalGradient(
                                    easing = EaseOut,
                                    startY = 0f,
                                    startIntensity = if (!isReversed) 0.8f else 0f,
                                    endY = solidHeightPx + progressiveHeightPx,
                                    endIntensity = if (!isReversed) 0f else 0.8f
                                )
                            }
                        } else Modifier
                    )
                    .background(
                        brush = Brush.verticalGradient(
                            colors = with(
                                listOf(
                                    Transparent,
                                    back.copy(alpha = .9f),
                                )
                            ) { if (!isReversed) this.asReversed() else this },
                            startY = 0f,
                            endY = solidHeightPx + shadowHeightPx
                        )
                    )
            )
        }
    }
}

@OptIn(ExperimentalHazeApi::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun ScrollEdgeFadeHorizontal(
    modifier: Modifier = Modifier,
    solidWidth: Dp = 0.dp,
    progressiveWidth: Dp = ScrollEdgeProgressiveHeight.big, // Можно создать отдельные константы для горизонтального случая
    shadowWidth: Dp = ScrollEdgeShadowHeight.big,
    isVisible: Boolean = true,
    isReversed: Boolean = false,
    hazeState: HazeState?,
) {
    val back = colorScheme.background
    val density = LocalDensity.current

    // More compact
    val (solidWidthPx, progressiveWidthPx, shadowWidthPx) = with(density) {
        listOf(solidWidth.toPx(), progressiveWidth.toPx(), shadowWidth.toPx())
    }

    AnimatedVisibility(
        modifier = modifier, visible = isVisible,
        enter = fadeIn(tween(600)),
        exit = fadeOut(tween(800))
    ) {
        Row(Modifier) {
            Box(
                Modifier.fillMaxHeight().width(solidWidth + max(shadowWidth, progressiveWidth))
                    .then(
                        if (hazeState != null) {
                            Modifier.hazeEffect(
                                hazeState, HazeMaterials.thick(colorScheme.background)
                            ) {
                                this.noiseFactor = 0f
                                this.inputScale = HazeInputScale.Fixed(1f)

                                progressive = HazeProgressive.horizontalGradient(
                                    easing = EaseOut,
                                    startX = 0f,
                                    startIntensity = if (!isReversed) 0.8f else 0f,
                                    endX = solidWidthPx + progressiveWidthPx,
                                    endIntensity = if (!isReversed) 0f else 0.8f
                                )
                            }
                        } else Modifier
                    )
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = with(
                                listOf(
                                    Transparent,
                                    back.copy(alpha = .9f),
                                )
                            ) { if (!isReversed) this.asReversed() else this },
                            startX = 0f,
                            endX = solidWidthPx + shadowWidthPx
                        )
                    )
            )
        }
    }
}