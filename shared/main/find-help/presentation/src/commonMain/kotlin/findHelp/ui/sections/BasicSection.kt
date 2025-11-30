package findHelp.ui.sections

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import common.detailsInterfaces.DetailsConfig
import common.grid.ColumnHeader
import common.grid.ContentType
import common.grid.defaults.DefaultItemsContent
import common.grid.defaults.DefaultRequestsContent
import common.itemDetailsTransition.ItemDetailsAnimator
import entities.FindHelpBasic
import network.NetworkState

@Suppress("FunctionName")
internal fun LazyGridScope.BasicSection(
    basic: NetworkState<FindHelpBasic>,
    sharedTransitionScope: SharedTransitionScope,
    itemDetailsAnimator: ItemDetailsAnimator,
    onClick: (DetailsConfig) -> Unit,
    refreshClick: () -> Unit
) {
    when (basic) {
        NetworkState.AFK -> {}
        is NetworkState.Error -> {
            ColumnHeader(
                key = "basicError",
                text = "Ошибка"
            ) {
                Text(
                    basic.prettyPrint,
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

        is NetworkState.Success<FindHelpBasic> -> {
            val readyToHelp = basic.data.readyToHelp
            val myRequests = basic.data.myRequests

            if (readyToHelp.isNotEmpty()) {
                DefaultItemsContent(
                    items = readyToHelp,
                    contentType = ContentType.ReadyToHelp,
                    sharedTransitionScope = sharedTransitionScope,
                    itemDetailsAnimator = itemDetailsAnimator,
                    onCardClicked = onClick
                )
            }
            if (myRequests.isNotEmpty()) {
                DefaultRequestsContent(
                    requests = myRequests,
                    contentType = ContentType.MyRequests,
                    sharedTransitionScope = sharedTransitionScope,
                    onCardClicked = onClick
                )
            }
        }
    }
}