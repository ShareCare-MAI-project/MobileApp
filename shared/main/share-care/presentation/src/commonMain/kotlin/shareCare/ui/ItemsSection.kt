package shareCare.ui

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import common.detailsInterfaces.DetailsConfig
import common.grid.ColumnHeader
import common.grid.ContentType
import common.grid.DefaultItemsContent
import common.itemDetailsTransition.ItemDetailsAnimator
import entities.ShareCareItems
import network.NetworkState

@Suppress("FunctionName")
internal fun LazyGridScope.ItemsSection(
    items: NetworkState<ShareCareItems>,
    sharedTransitionScope: SharedTransitionScope,
    itemDetailsAnimator: ItemDetailsAnimator,
    onClick: (DetailsConfig.ItemDetailsConfig) -> Unit
) {
    when (items) {
        NetworkState.AFK -> {}
        is NetworkState.Error -> {
            ColumnHeader(
                key = "itemsError",
                text = "Предметы"
            ) {
                Text(
                    items.prettyPrint,
                    textAlign = TextAlign.Center,
                )
                Button(onClick = {}) {
                    Text("Ещё раз")
                }
            }
        }

        NetworkState.Loading -> {
            item(key = "itemsLoading") {
                Text(text = "Loading", modifier = Modifier.animateItem())
            }
        }

        is NetworkState.Success<ShareCareItems> -> {
            val responses = items.data.responses
            val myPublishedItems = items.data.myPublishedItems

            if (responses.isNotEmpty()) {
                DefaultItemsContent(
                    items = responses,
                    contentType = ContentType.Responses,
                    sharedTransitionScope = sharedTransitionScope,
                    itemDetailsAnimator = itemDetailsAnimator,
                    onCardClicked = onClick
                )
            }
            if (myPublishedItems.isNotEmpty()) {
                DefaultItemsContent(
                    items = myPublishedItems,
                    contentType = ContentType.MyItems,
                    sharedTransitionScope = sharedTransitionScope,
                    itemDetailsAnimator = itemDetailsAnimator,
                    onCardClicked = onClick
                )
            }
        }
    }
}