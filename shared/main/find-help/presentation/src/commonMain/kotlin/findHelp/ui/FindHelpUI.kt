package findHelp.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import common.grid.MainLazyGrid
import common.itemDetailsTransition.LocalItemDetailsAnimator
import common.search.Filters
import findHelp.components.FindHelpComponent
import findHelp.ui.sections.BasicSection
import findHelp.ui.sections.SearchSection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import utils.GridEndHandler
import view.consts.Paddings

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.FindHelpUI(
    lazyGridState: LazyGridState,
    component: FindHelpComponent
) {
    val itemDetailsAnimator = LocalItemDetailsAnimator.current


    val basic by component.basic.collectAsState()

    val searchItems by component.items.collectAsState()
    val searchData by component.searchData.collectAsState()
    val searchHasMoreItems by component.searchHasMoreItems.collectAsState()

    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()




    GridEndHandler(
        gridState = lazyGridState,
        preFetchItems = 2,
        hasMoreItems = searchHasMoreItems,
        isLoading = searchItems.isLoading(),
        items = searchItems
    ) {
        component.onSearch(resetItems = false)
        println("EDNx")
    }

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

        SearchSection(
            searchResponse = searchItems,
            sharedTransitionScope = this@FindHelpUI,
            itemDetailsAnimator = itemDetailsAnimator,
            onClick = component.openDetails,
            refreshClick = { component.onSearch(resetItems = true) },
            myId = component.myId,
            filters = {
                Filters(
                    deliveryTypes = searchData.deliveryTypes,
                    category = searchData.category,
                    onDeliveryTypeClick = { clicked ->
                        if (clicked != null) {
                            val deliveryTypes = searchData.deliveryTypes.toMutableList()
                            if (clicked in deliveryTypes) deliveryTypes.remove(clicked)
                            else deliveryTypes.add(clicked)
                            component.onDeliveryTypesChange(deliveryTypes)
                        } else component.onDeliveryTypesChange(listOf())
                    },
                    onCategoryClick = { component.onCategoryChange(it) }
                )
            }
        )
    }
    Text(searchHasMoreItems.toString(), modifier = Modifier.padding(Paddings.big))
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