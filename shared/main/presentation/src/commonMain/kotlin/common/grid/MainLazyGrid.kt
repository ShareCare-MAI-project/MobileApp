package common.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import utils.SpacerV
import view.consts.Paddings
import view.consts.Sizes


@Composable
internal fun MainLazyGrid(
    topPadding: Dp,
    emptyBottomPadding: Dp, // не идёт в тень, а просто добавляет отступ в конце списка
    lazyGridState: LazyGridState = rememberLazyGridState(),
    content: LazyGridScope.() -> Unit
) {

    val isScrollable = (lazyGridState.canScrollForward || lazyGridState.canScrollBackward)

    LazyVerticalGrid(
        columns = GridCells.Adaptive(Sizes.columnWidth),
        state = lazyGridState,
        contentPadding = PaddingValues(horizontal = Paddings.semiMedium),
        verticalArrangement = Arrangement.spacedBy(Paddings.small),
        horizontalArrangement = Arrangement.spacedBy(Paddings.small)
    ) {
        item(key = "topPadding", span = { GridItemSpan(maxCurrentLineSpan) }) {
            SpacerV(topPadding)
        }
        content()

        if (isScrollable) {
            item(key = "bottomPadding", span = { GridItemSpan(maxCurrentLineSpan) }) {
                SpacerV(emptyBottomPadding)
            }
        }
    }

}