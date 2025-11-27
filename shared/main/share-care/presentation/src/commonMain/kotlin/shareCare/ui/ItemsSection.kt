package shareCare.ui

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import common.detailsTransition.DetailsAnimator
import common.grid.ColumnHeader
import common.grid.ContentType
import common.grid.DefaultItemsContent
import entities.ShareCareItems
import network.NetworkState

@Suppress("FunctionName")
internal fun LazyGridScope.ItemsSection(
    items: NetworkState<ShareCareItems>,
    sharedTransitionScope: SharedTransitionScope,
    detailsAnimator: DetailsAnimator
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
                    detailsAnimator = detailsAnimator
                ) {}
            }
            if (myPublishedItems.isNotEmpty()) {
                DefaultItemsContent(
                    items = myPublishedItems,
                    contentType = ContentType.MyItems,
                    sharedTransitionScope = sharedTransitionScope,
                    detailsAnimator = detailsAnimator
                ) {}
            }
        }
    }
}