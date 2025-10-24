package common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import utils.SpacerV
import utils.fastBackground


@Composable
internal fun MainLazyColumn(
    topPadding: Dp,
    emptyBottomPadding: Dp, // не идёт в тень, а просто добавляет отступ в конце списка
    lazyListState: LazyListState = rememberLazyListState(),
    content: LazyListScope.() -> Unit
) {

    val isScrollable = (lazyListState.canScrollForward || lazyListState.canScrollBackward)

    Box() {
        LazyColumn(
            modifier = Modifier.fillMaxSize().fastBackground(colorScheme.background),
            state = lazyListState,
            horizontalAlignment = Alignment.Start
        ) {
            item(key = "topPadding") {
                SpacerV(topPadding)
            }
            content()

            if (isScrollable) {
                item(key = "bottomPadding") {
                    SpacerV(emptyBottomPadding)
                }
            }
        }
    }
}