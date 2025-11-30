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
import utils.GridEndHandler

@Composable
fun SharedTransitionScope.ShareCareUI(
    lazyGridState: LazyGridState,
    component: ShareCareComponent,
) {

    val items by component.items.collectAsState()

    val itemDetailsAnimator = LocalItemDetailsAnimator.current
    val searchQuery by component.query.collectAsState()
    val isSearchActive = searchQuery.isNotEmpty()

    var dummies by remember { mutableStateOf(listOf<Int>()) }
    GridEndHandler(
        gridState = lazyGridState
    ) {

        if (dummies.size < 50) {
            dummies += listOf(
                dummies.lastIndex + 1,
                dummies.lastIndex + 2,
                dummies.lastIndex + 3,
                dummies.lastIndex + 4,
                dummies.lastIndex + 5,
                dummies.lastIndex + 6,
                dummies.lastIndex + 7,
                dummies.lastIndex + 8,
                dummies.lastIndex + 9,
                dummies.lastIndex + 10,
                dummies.lastIndex + 11,
                dummies.lastIndex + 12,
                dummies.lastIndex + 13,
                dummies.lastIndex + 14,
                dummies.lastIndex + 15,
                dummies.lastIndex + 16,
                dummies.lastIndex + 17,
                dummies.lastIndex + 18,
                dummies.lastIndex + 19,
                dummies.lastIndex + 20,
                dummies.lastIndex + 21,
            )
        }
    }

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
