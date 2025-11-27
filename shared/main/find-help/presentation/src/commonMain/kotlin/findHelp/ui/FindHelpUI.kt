package findHelp.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import common.detailsTransition.LocalDetailsAnimator
import common.grid.ContentType
import common.grid.MainLazyGrid
import common.grid.TransitionColumnHeader
import findHelp.components.FindHelpComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.FindHelpUI(
    lazyGridState: LazyGridState,
    component: FindHelpComponent
) {
    val detailsAnimator = LocalDetailsAnimator.current
    val items = remember { (0..50).toList().toMutableStateList() }

    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    MainLazyGrid(
        lazyGridState = lazyGridState,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = !isRefreshing
            coroutineScope.launch {
                delay(3000)
                isRefreshing = false
            }
        }

    ) {
        TransitionColumnHeader(
            contentType = ContentType.ReadyToHelp
        )

//        items(items = items.toList(), key = { it }, contentType = { ContentType.ReadyToHelp }) {
//            val id = "meow_$it"
//            ItemCard(
//                modifier = Modifier
//                    .animateItem()
//                    .fillMaxSize(),
//                title = "$cardTitle #${it}",
//                id = id,
//                detailsAnimator = detailsAnimator
//            ) {
//                component.onCardClicked(id)
//            }
//        }

        TransitionColumnHeader(
            contentType = ContentType.Catalog
        )
        items(50, key = { (it + 1) * 100 }, contentType = { ContentType.Catalog }) {
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