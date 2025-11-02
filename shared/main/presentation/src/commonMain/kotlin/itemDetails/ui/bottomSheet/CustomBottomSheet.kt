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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import flow.ui.DetailedItemAnimationManager
import view.consts.Paddings
import view.consts.Sizes

enum class SheetValue { Collapsed, Expanded }

fun SheetValue.isExpanded() = this == SheetValue.Expanded


@Composable
fun rememberCustomSheetState(heightPx: Float, key: Any?) = remember(key) {
    AnchoredDraggableState(
        initialValue = SheetValue.Expanded,
        anchors = DraggableAnchors {
            SheetValue.Expanded at 0f
            SheetValue.Collapsed at heightPx
        },
    )
}


@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun CustomBottomSheet(
    detailedItemAnimationManager: DetailedItemAnimationManager,
    hazeState: HazeState,
    modifier: Modifier,
    content: @Composable ColumnScope.() -> Unit
) {

    val isDarkTheme = isSystemInDarkTheme()

    val density = LocalDensity.current


    val dragInteractionSource = remember { MutableInteractionSource() }

    val offset = detailedItemAnimationManager.sheetState.requireOffset()

    val dragPressed = dragInteractionSource.collectIsPressedAsState().value
    val dragDragged = dragInteractionSource.collectIsDraggedAsState().value


    val endIntensity = if (isDarkTheme) .7f else .6f

    val startIntensity by animateFloatAsState(
        if (dragPressed) endIntensity - .45f
        else if (offset != 0f || dragDragged) endIntensity - .2f
        else .0f,
        animationSpec = tween(600)
    )


    LaunchedEffect(offset) {
        val newBackProgress = offset / detailedItemAnimationManager.sheetHeightPx
        detailedItemAnimationManager.onSheetDrag(newBackProgress.coerceIn(0f, 1f))
    }

    Column(
        modifier = modifier
            .height(detailedItemAnimationManager.sheetHeight)
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
                        50.dp.toPx()
                    },
                    startIntensity = startIntensity,
                    endIntensity = endIntensity
                )

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
                    detailedItemAnimationManager.sheetState,
                    orientation = Orientation.Vertical,
                    interactionSource = dragInteractionSource
                )
                .clickable(dragInteractionSource, null) {}
        )
        Text(detailedItemAnimationManager.sheetState.offset.toString())
        Text(offset.toString())

        content()
    }
}