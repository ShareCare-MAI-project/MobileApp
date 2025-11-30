package ui

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import common.grid.MainLazyGrid
import common.itemDetailsTransition.LocalItemDetailsAnimator
import shareCare.components.ShareCareComponent
import shareCare.ui.sections.ItemsSection

@Composable
fun SharedTransitionScope.ShareCareUI(
    lazyGridState: LazyGridState,
    component: ShareCareComponent,
) {

    val items by component.items.collectAsState()

    val itemDetailsAnimator = LocalItemDetailsAnimator.current

    MainLazyGrid(
        lazyGridState = lazyGridState,
    ) {
        ItemsSection(
            items,
            sharedTransitionScope = this@ShareCareUI,
            itemDetailsAnimator = itemDetailsAnimator,
            onClick = component.openDetails,
            refreshClick = component::fetchItems
        )
//
//        TransitionColumnHeader(
//            contentType = ContentType.MyItems
//        )
//        items(50, key = { (it + 1) * 100 }, contentType = { ContentType.MyItems }) {
//            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
//                (1..3).forEach { _ ->
//                    Text("ShareCare")
//                }
//            }
//        }
    }

}
