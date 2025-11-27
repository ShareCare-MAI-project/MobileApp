package common.grid

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.Modifier
import common.detailsTransition.DetailsAnimator
import common.itemCard.ItemCard
import entity.ItemResponse

@Suppress("FunctionName")
fun LazyGridScope.DefaultItemsContent(
    items: List<ItemResponse>,
    contentType: ContentType,
    sharedTransitionScope: SharedTransitionScope,
    detailsAnimator: DetailsAnimator,
    onCardClicked: (String) -> Unit
) {

    TransitionColumnHeader(
        contentType = contentType
    )
    items(
        items = items,
        key = { it.id },
        contentType = { contentType }) { item ->
        with(sharedTransitionScope) {
            ItemCard(
                modifier = Modifier
                    .animateItem()
                    .fillMaxSize(),
                title = item.title,
                id = item.id,
                imagePath = item.images.firstOrNull(),
                location = item.location,
                detailsAnimator = detailsAnimator
            ) {
                onCardClicked(item.id)
            }
        }
    }
}