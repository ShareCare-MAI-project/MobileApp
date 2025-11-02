package itemDetails.ui.bottomSheet

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableDefaults
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.snapping.snapFlingBehavior
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import view.consts.Paddings
import view.consts.Sizes

enum class SheetValue { Collapsed, Expanded }


@Composable
fun rememberCustomSheetState(heightPx: Float) = remember {
    AnchoredDraggableState(
        initialValue = SheetValue.Expanded,
        anchors = DraggableAnchors {
            SheetValue.Expanded at 0f
            SheetValue.Collapsed at heightPx
        },
    )
}

@Composable
fun rememberCustomSheetState(height: Dp) =
    rememberCustomSheetState(with(LocalDensity.current) { height.toPx() })

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun CustomBottomSheet(
    anchoredState: AnchoredDraggableState<SheetValue>,
    backProgress: Float,
    height: Dp,
    heightPx: Float,
    hazeState: HazeState,
    modifier: Modifier,
    onDrag: (Float) -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {

    val isDarkTheme = isSystemInDarkTheme()

    val startIntensity by animateFloatAsState(
        if (false) .0f else .4f,
        animationSpec = tween(600)
    )

    val density = LocalDensity.current


    val xt = 50.dp

    val threshold = with(density) { heightPx - 100.dp.toPx() }

    val flingBehaviour = remember(anchoredState, density) {
        snapFlingBehavior(
            AnchoredDraggableLayoutInfoProvider(
                state = anchoredState,
                positionalThreshold = { it * .5f },
                velocityThreshold = { with(density) { 200.dp.toPx() } }
            ),
            decayAnimationSpec = AnchoredDraggableDefaults.DecayAnimationSpec,
            snapAnimationSpec = AnchoredDraggableDefaults.SnapAnimationSpec,
        )
    }

    val isInBackGesture = backProgress > 0


    val offset = if (isInBackGesture) {
        (backProgress * heightPx)
    } else {
        anchoredState.requireOffset()
    }

    LaunchedEffect(offset, isInBackGesture) {
        if (!isInBackGesture) {
            val newBackProgress = offset / heightPx
            onDrag(newBackProgress.coerceIn(0f, 1f))
        }
    }


    println("offset: $offset  bg: $isInBackGesture mewo $backProgress")

    Column(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .then(modifier)
            .clip(shapes.extraExtraLarge)

            .offset(y = with(density) {
                offset.toDp()
            })
            // HAZE
            .hazeEffect(
                state = hazeState,
                style = HazeMaterials.thick(colorScheme.background)
            ) {
                progressive = HazeProgressive.verticalGradient(
                    endY = with(density) {
                        50.dp.toPx()
                    },
                    startIntensity = startIntensity,
                    endIntensity = if (isDarkTheme) .7f else .6f
                )

            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier.padding(top = Paddings.semiMedium).size(Sizes.dragHandleSize)
                .clip(shapes.medium).background(
                    colorScheme.onBackground.copy(
                        alpha = (startIntensity + .6f).coerceAtMost(1f)
                    )
                )
                .anchoredDraggable(
                    anchoredState, orientation = Orientation.Vertical,
                    flingBehavior = flingBehaviour
                )
        )

        content()
    }
}