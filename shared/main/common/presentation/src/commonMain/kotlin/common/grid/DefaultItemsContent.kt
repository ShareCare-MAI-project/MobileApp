package common.grid

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.Modifier
import common.detailsInterfaces.DetailsConfig
import common.itemCard.ItemCard
import common.itemDetailsTransition.ItemDetailsAnimator
import entity.ItemResponse

@Suppress("FunctionName")
fun LazyGridScope.DefaultItemsContent(
    items: List<ItemResponse>,
    contentType: ContentType,
    sharedTransitionScope: SharedTransitionScope,
    itemDetailsAnimator: ItemDetailsAnimator,
    onCardClicked: (DetailsConfig.ItemDetailsConfig) -> Unit
) {

    TransitionColumnHeader(
        contentType = contentType
    )
    items(
        items = items,
        key = { it.id },
        contentType = { contentType }) { item ->
        val imagePath = item.images.firstOrNull() ?: ""
        with(sharedTransitionScope) {
            ItemCard(
                modifier = Modifier
                    .animateItem()
                    .fillMaxSize(),
                title = item.title,
                id = item.id,
                imagePath = imagePath,
                location = item.location,
                itemDetailsAnimator = itemDetailsAnimator
            ) {
                onCardClicked(
                    DetailsConfig.ItemDetailsConfig(
                        id = item.id,
                        images = item.images,
                        creatorId = item.ownerId,
                        title = item.title,
                        description = item.description,
                        location = item.location,
                        category = item.category,
                        deliveryTypes = item.deliveryTypes,
                        recipientId = item.recipientId
                    )
                )
            }
        }
    }
}