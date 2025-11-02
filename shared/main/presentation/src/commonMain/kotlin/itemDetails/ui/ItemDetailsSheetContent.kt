package itemDetails.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import dev.cardTitle
import dev.chrisbanes.haze.HazeState
import itemDetails.ui.bottomSheet.CustomBottomSheet
import itemDetails.ui.bottomSheet.SheetValue
import view.consts.Paddings

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BoxScope.ItemDetailsSheetContent(
    sheetState: AnchoredDraggableState<SheetValue>,
    backProgress: Float,
    hazeState: HazeState,
    sharedTransitionScope: SharedTransitionScope,
    sheetHeight: Dp,
    sheetHeightPx: Float,
    onDrag: (Float) -> Unit
) {
    CustomBottomSheet(
        anchoredState = sheetState,
        height = sheetHeight,
        heightPx = sheetHeightPx,
        hazeState = hazeState,
        modifier = with(sharedTransitionScope) {
            Modifier.align(Alignment.BottomCenter)
                .renderInSharedTransitionScopeOverlay(1f)
        },
        backProgress = backProgress,
        onDrag = onDrag
    ) {
        Text(
            text = cardTitle,
            textAlign = TextAlign.Center,
            style = typography.headlineLargeEmphasized,
            modifier = Modifier
                .padding(horizontal = Paddings.medium),
            fontWeight = FontWeight(450)
        )

        Column(Modifier.verticalScroll(rememberScrollState())) {
            for (i in 1..50) {
                Text("asdasdas")
            }
        }
    }
}