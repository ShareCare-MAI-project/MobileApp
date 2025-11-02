package findHelp.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import common.ContentType
import common.ItemCard
import common.MainLazyGrid
import common.TransitionColumnHeader
import findHelp.components.FindHelpComponent
import flow.ui.DetailedItemAnimationManager

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SharedTransitionScope.FindHelpUI(
    topPadding: Dp,
    bottomPadding: Dp,
    lazyGridState: LazyGridState,
    currentContentType: ContentType?,
    component: FindHelpComponent,
    detailedItemAnimationManager: DetailedItemAnimationManager
) {


    val items = remember { (0..50).toList().toMutableStateList() }

    var deletedCount by remember { mutableStateOf(0) }
    var deletedLast by remember { mutableStateOf(0) }

    MainLazyGrid(
        topPadding = topPadding,
        emptyBottomPadding = bottomPadding,
        lazyGridState = lazyGridState,
    ) {
        TransitionColumnHeader(
            contentType = ContentType.Catalog,
            currentContentType = currentContentType
        )

        items(items = items.toList(), key = { it }, contentType = { ContentType.Catalog }) {
            val id = "meow_$it"
            ItemCard(
                modifier = Modifier
                    .animateItem()
                    .fillMaxSize(),
                title = "Кроссовки Air Jordan 1 Low #${it}",
                id = id,
                detailedItemAnimationManager = detailedItemAnimationManager
            ) {
                component.onCardClicked(id)
            }
        }

        TransitionColumnHeader(
            contentType = ContentType.MyRequests,
            currentContentType = currentContentType
        )
        items(50, key = { (it + 1) * 100 }, contentType = { ContentType.MyRequests }) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                (1..3).forEach { _ ->
                    Text("FindHelp")
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun FindHelpUIPreview(
//    topSafePadding: Dp,
//    bottomShadowHeight: Dp,
//    emptyBottomPadding: Dp,
//    hazeState: HazeState,
//) {
//    FindHelpUI(
//        topSafePadding = topSafePadding,
//        bottomShadowHeight = bottomShadowHeight,
//        emptyBottomPadding = emptyBottomPadding,
//        hazeState = hazeState,
//        component = FakeFindHelpComponent
//    )
//}