package findHelp.ui.sections

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import common.detailsInterfaces.DetailsConfig
import common.grid.ColumnHeader
import common.grid.ContentType
import common.grid.defaults.DefaultItemsContent
import common.itemDetailsTransition.ItemDetailsAnimator
import entity.ItemResponse
import network.NetworkState

@Suppress("FunctionName")
internal fun LazyGridScope.SearchSection(
    searchResponse: NetworkState<List<ItemResponse>>,
    filters: @Composable () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    itemDetailsAnimator: ItemDetailsAnimator,
    myId: String,
    onClick: (DetailsConfig) -> Unit,
    refreshClick: () -> Unit
) {
    when (searchResponse) {
        NetworkState.AFK -> {}
        is NetworkState.Error -> {
            ColumnHeader(
                key = "searchError",
                text = "Ошибка"
            ) {
                Text(
                    searchResponse.prettyPrint,
                    textAlign = TextAlign.Center,
                )
                Button(onClick = refreshClick) {
                    Text("Ещё раз")
                }
            }
        }

        NetworkState.Loading -> {
            item(key = "basicLoading") {
                Text(text = "Loading", modifier = Modifier.animateItem())
            }
        }

        is NetworkState.Success<List<ItemResponse>> -> {
            val items = searchResponse.data

            DefaultItemsContent(
                items = items,
                contentType = ContentType.Catalog,
                sharedTransitionScope = sharedTransitionScope,
                itemDetailsAnimator = itemDetailsAnimator,
                onCardClicked = onClick,
                filters = filters,
                myId = myId
            )

        }
    }
}