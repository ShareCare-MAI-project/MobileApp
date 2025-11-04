package shareCare.ui

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
import common.detailsTransition.LocalDetailsAnimator
import common.grid.ContentType
import common.grid.MainLazyGrid
import common.grid.TransitionColumnHeader
import common.itemCard.ItemCard
import dev.cardTitle
import shareCare.components.ShareCareComponent

@Composable
fun SharedTransitionScope.ShareCareUI(
    lazyGridState: LazyGridState,
    component: ShareCareComponent,
) {
    val detailsAnimator = LocalDetailsAnimator.current
    val items = remember { (0..50).toList().toMutableStateList() }

    MainLazyGrid(
        lazyGridState = lazyGridState,
    ) {
        TransitionColumnHeader(
            contentType = ContentType.Responses
        )

        items(items = items.toList(), key = { it }, contentType = { ContentType.Responses }) {
            val id = "meow_$it"

            ItemCard(
                modifier = Modifier
                    .animateItem()
                    .fillMaxSize(),
                title = "$cardTitle #${it}",
                id = id,
                detailsAnimator = detailsAnimator
            ) {
//                component.onCardClicked(id)
            }
        }

        TransitionColumnHeader(
            contentType = ContentType.MyItems
        )
        items(50, key = { (it + 1) * 100 }, contentType = { ContentType.MyItems }) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                (1..3).forEach { _ ->
                    Text("ShareCare")
                }
            }
        }
    }

}
