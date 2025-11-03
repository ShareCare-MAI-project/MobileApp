package itemDetails.ui.bottomSheet

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.HazeInputScale
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import view.consts.Paddings
import view.consts.Sizes

enum class SheetValue { Collapsed, Expanded }

fun SheetValue.isExpanded() = this == SheetValue.Expanded


@Composable
fun rememberCustomSheetState(heightPx: Float, key: Any?) = remember(key) {
    AnchoredDraggableState(
        initialValue = SheetValue.Collapsed,
        anchors = DraggableAnchors {
            SheetValue.Expanded at 0f
            SheetValue.Collapsed at heightPx
        },
    )
}


@OptIn(ExperimentalHazeMaterialsApi::class, ExperimentalHazeApi::class)
@Composable
fun CustomBottomSheet(
    sheetState: AnchoredDraggableState<SheetValue>,
    height: Dp,
    hazeState: HazeState,
    modifier: Modifier,
    onDrag: (Float) -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {

    val isDarkTheme = isSystemInDarkTheme()

    val density = LocalDensity.current


    val dragInteractionSource = remember { MutableInteractionSource() }

    val offset = sheetState.requireOffset()

    val dragPressed = dragInteractionSource.collectIsPressedAsState().value
    val dragDragged = dragInteractionSource.collectIsDraggedAsState().value


    val endIntensity = if (isDarkTheme) .8f else .7f

    val startIntensity by animateFloatAsState(
        if (dragPressed) endIntensity - .45f
        else if (offset != 0f || dragDragged) endIntensity
        else .0f,
        animationSpec = tween(700)
    )

    val adaptiveInputScale = with(density) {
        if (offset != 0f || dragDragged) .3f
        else 1f
    }

    LaunchedEffect(offset) {
        onDrag(offset)
    }

    Column(
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .offset(y = with(density) {
                offset.toDp()
            })
            .pointerInput(Unit) {} // Prohibit Background scrolling

            .clip(shapes.extraExtraLarge)
            // HAZE
            .hazeEffect(
                state = hazeState,
                style = HazeMaterials.thick(colorScheme.background)
            ) {
                progressive = HazeProgressive.verticalGradient(
                    endY = with(density) {
                        50.dp.toPx() * adaptiveInputScale // remove fade line, when change inputScale
                    },
                    startIntensity = startIntensity,
                    endIntensity = endIntensity,
                    preferPerformance = false
                )
                inputScale = HazeInputScale.Fixed(adaptiveInputScale)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier.padding(top = Paddings.semiMedium).size(Sizes.dragHandleSize)
                .clip(shapes.medium)
                .background(
                    colorScheme.onBackground.copy(
                        alpha = (startIntensity + .6f).coerceAtMost(1f)
                    )
                )
                .anchoredDraggable(
                    sheetState,
                    orientation = Orientation.Vertical,
                    interactionSource = dragInteractionSource
                )
                .clickable(dragInteractionSource, null) {}
        )

        content()
    }
}