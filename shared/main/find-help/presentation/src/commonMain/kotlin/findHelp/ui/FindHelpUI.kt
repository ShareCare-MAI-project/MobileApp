package findHelp.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import common.grid.MainLazyGrid
import common.itemDetailsTransition.LocalItemDetailsAnimator
import findHelp.components.FindHelpComponent
import findHelp.ui.sections.BasicSection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.FindHelpUI(
    lazyGridState: LazyGridState,
    component: FindHelpComponent
) {
    val itemDetailsAnimator = LocalItemDetailsAnimator.current
    val items = remember { (0..50).toList().toMutableStateList() }


    val basic by component.basic.collectAsState()

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

        BasicSection(
            basic = basic,
            sharedTransitionScope = this@FindHelpUI,
            itemDetailsAnimator = itemDetailsAnimator,
            onClick = component.openDetails,
            refreshClick = { component.fetchBasic() }
        )

//        items(items = items.toList(), key = { it }, contentType = { ContentType.MyRequests }) {
//            val id = "meow_$it"
//            RequestCard(
//                modifier = Modifier
//                    .animateItem()
//                    .fillMaxSize(),
//                id = id,
//                text = "Нужны штаны на зиму, рост 167",
//                location = "Москва, метро Сокол",
//                organizationName = null
//            ) {
//                component.openDetails(DetailsConfig.RequestDetailsConfig(
//                    id = id
//                ))
//            }
//
//        }
//
//        TransitionColumnHeader(
//            contentType = ContentType.Catalog
//        )
//        items(50, key = { (it + 1) * 100 }, contentType = { ContentType.Catalog }) {
//            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
//                (1..3).forEach { _ ->
//                    Text("FindHelp")
//                }
//            }
//        }
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