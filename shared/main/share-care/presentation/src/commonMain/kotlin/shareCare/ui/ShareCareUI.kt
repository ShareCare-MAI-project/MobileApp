package shareCare.ui

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    val searchData by component.searchData.collectAsState()
    val isSearchActive = searchData.query.isNotEmpty()

    var dummies by remember { mutableStateOf(listOf<Int>()) }


    MainLazyGrid(
        lazyGridState = lazyGridState,
    ) {
        if (!isSearchActive) {
            ItemsSection(
                items,
                sharedTransitionScope = this@ShareCareUI,
                itemDetailsAnimator = itemDetailsAnimator,
                onClick = component.openDetails,
                refreshClick = component::fetchItems
            )
        }
        items(dummies, key = { it }) {
            Text(text = "meowmeow$it", modifier = Modifier.height(100.dp))
        }
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
