package common.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import utils.SpacerV
import utils.fastBackground
import view.consts.Paddings
import view.consts.Sizes


data class SpacePaddings(
    val top: Dp,
    val bottom: Dp
)

val LocalSpacePaddings: ProvidableCompositionLocal<SpacePaddings> =
    staticCompositionLocalOf {
        error("No SpacePaddings provided")
    }

@Composable
fun MainLazyGrid(
    lazyGridState: LazyGridState = rememberLazyGridState(),
    content: LazyGridScope.() -> Unit
) {
    val spacePaddings = LocalSpacePaddings.current
    val isScrollable = (lazyGridState.canScrollForward || lazyGridState.canScrollBackward)

    LazyVerticalGrid(
        columns = GridCells.Adaptive(Sizes.columnWidth),
        state = lazyGridState,
        contentPadding = PaddingValues(horizontal = Paddings.horizontalListPadding),
        verticalArrangement = Arrangement.spacedBy(Paddings.small),
        horizontalArrangement = Arrangement.spacedBy(Paddings.small),
        modifier = Modifier.fillMaxSize().fastBackground(colorScheme.background)
    ) {
        item(key = "topPadding", span = { GridItemSpan(maxLineSpan) }) {
            SpacerV(spacePaddings.top)
        }
        content()

        if (isScrollable) {
            item(key = "bottomPadding", span = { GridItemSpan(maxLineSpan) }) {
                SpacerV(spacePaddings.bottom)
            }
        }
    }

}