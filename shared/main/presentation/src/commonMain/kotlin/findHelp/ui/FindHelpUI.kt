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
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import common.detailsTransition.DetailsAnimator
import common.grid.ContentType
import common.grid.MainLazyGrid
import common.grid.TransitionColumnHeader
import common.itemCard.ItemCard
import dev.cardTitle
import findHelp.components.FindHelpComponent

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SharedTransitionScope.FindHelpUI(
    topPadding: Dp,
    bottomPadding: Dp,
    lazyGridState: LazyGridState,
    currentContentType: ContentType?,
    component: FindHelpComponent,
    detailsAnimator: DetailsAnimator
) {


    val items = remember { (0..50).toList().toMutableStateList() }

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
                title = "$cardTitle #${it}",
                id = id,
                detailsAnimator = detailsAnimator
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