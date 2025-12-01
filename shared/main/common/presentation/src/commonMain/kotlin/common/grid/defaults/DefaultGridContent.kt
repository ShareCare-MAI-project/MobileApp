package common.grid.defaults

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import common.grid.ContentType
import common.grid.TransitionColumnHeader

@Suppress("FunctionName")
fun <T> LazyGridScope.DefaultGridContent(
    items: List<T>,
    key: (T) -> Any,
    contentType: ContentType,
    filters: @Composable (() -> Unit)?,
    cardContent: @Composable LazyGridItemScope.(T) -> Unit
) {
    TransitionColumnHeader(
        contentType = contentType
    )

    filters?.let {
        item(key = "Filters", span = { GridItemSpan(maxLineSpan) }) {
            filters()
        }
    }

    items(
        items = items,
        key = key,
        contentType = { contentType }
    ) { item ->
        cardContent(item)
    }
}