package itemDetails.ui.bottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import view.consts.Paddings
import view.consts.Sizes

@Composable
fun DragHandle(
    startIntensity: Float,
    sheetState: AnchoredDraggableState<SheetValue>,
    dragInteractionSource: MutableInteractionSource,
    pagerState: PagerState
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
}