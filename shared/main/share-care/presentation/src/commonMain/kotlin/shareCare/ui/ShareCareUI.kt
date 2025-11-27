package ui

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import common.detailsTransition.LocalDetailsAnimator
import common.grid.MainLazyGrid
import shareCare.components.ShareCareComponent
import shareCare.ui.ItemsSection

@Composable
fun SharedTransitionScope.ShareCareUI(
    lazyGridState: LazyGridState,
    component: ShareCareComponent,
) {

    val items by component.items.collectAsState()

    val detailsAnimator = LocalDetailsAnimator.current

    MainLazyGrid(
        lazyGridState = lazyGridState,
    ) {
        ItemsSection(
            items,
            sharedTransitionScope = this@ShareCareUI,
            detailsAnimator = detailsAnimator
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
