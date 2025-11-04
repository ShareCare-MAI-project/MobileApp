package itemDetails.ui.bottomSheet

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import common.detailsTransition.DetailsAnimator
import dev.cardTitle
import dev.chrisbanes.haze.HazeState
import itemDetails.ui.ItemDetailsDefaults
import view.consts.Paddings

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BoxScope.ItemDetailsSheetContent(
    hazeState: HazeState,
    sharedTransitionScope: SharedTransitionScope,
    detailsAnimator: DetailsAnimator
) {
    val density = LocalDensity.current

    val gapHeightPx = remember { with(density) { ItemDetailsDefaults.gapHeight.toPx() } }

    CustomBottomSheet(
        hazeState = hazeState,
        modifier = with(sharedTransitionScope) {
            Modifier.align(Alignment.BottomCenter)
                .renderInSharedTransitionScopeOverlay(1f)
        },
        sheetState = detailsAnimator.sheetState,
        height = detailsAnimator.sheetHeight,
        pagerState = detailsAnimator.pagerState,
        onDrag = { offset ->
            detailsAnimator.onSheetDrag {
                val newBackProgress =
                    with(density) { (offset - gapHeightPx) / (detailsAnimator.sheetHeightPx - gapHeightPx) }
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
            fontWeight = FontWeight.Medium
        )

        Column(Modifier.verticalScroll(rememberScrollState())) {
            for (i in 1..50) {
                Text("asdasdas")
            }
        }
    }
}