package itemDetails.ui.bottomSheet

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.cardTitle
import dev.chrisbanes.haze.HazeState
import flow.ui.DetailedItemAnimationManager
import view.consts.Paddings

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BoxScope.ItemDetailsSheetContent(
    hazeState: HazeState,
    sharedTransitionScope: SharedTransitionScope,
    detailedItemAnimationManager: DetailedItemAnimationManager
) {
    val density = LocalDensity.current
    CustomBottomSheet(
        hazeState = hazeState,
        modifier = with(sharedTransitionScope) {
            Modifier.align(Alignment.BottomCenter)
                .renderInSharedTransitionScopeOverlay(1f)
        },
        sheetState = detailedItemAnimationManager.sheetState,
        height = detailedItemAnimationManager.sheetHeight,
        onDrag = { offset ->
            detailedItemAnimationManager.onSheetDrag {
                val newBackProgress =
                    with(density) { (offset - 50.dp.toPx()) / (detailedItemAnimationManager.sheetHeightPx - 50.dp.toPx()) }
                newBackProgress.coerceIn(0f, 1f)
            }
        }
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