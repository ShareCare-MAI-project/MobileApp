package common.grid.defaults

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.ui.Modifier
import common.detailsInterfaces.DetailsConfig
import common.grid.ContentType
import common.grid.TransitionColumnHeader
import common.grid.search.Filters
import common.requestCard.RequestCard
import entity.RequestResponse

@Suppress("FunctionName")
fun LazyGridScope.DefaultRequestsContent(
    requests: List<RequestResponse>,
    contentType: ContentType,
    sharedTransitionScope: SharedTransitionScope,
    onCardClicked: (DetailsConfig.RequestDetailsConfig) -> Unit
) {


    TransitionColumnHeader(
        contentType = ContentType.Catalog
    )
    item(key = "Filters", span = { GridItemSpan(maxLineSpan) }) {
        Filters(
            deliveryTypes = listOf(),
            category = null
        )
    }
    DefaultGridContent(
        items = requests,
        key = { it.id },
        contentType = contentType
    ) { request ->
        with(sharedTransitionScope) {
            RequestCard(
                modifier = Modifier
                    .animateItem()
                    .fillMaxSize(),
                id = request.id,
                text = request.text,
                location = request.location,
                organizationName = request.organizationName
            ) {
                onCardClicked(
                    DetailsConfig.RequestDetailsConfig(
                        id = request.id,
                        text = request.text,
                        category = request.category,
                        location = request.location,
                        deliveryTypes = request.deliveryTypes,
                        organizationName = request.organizationName,
                        creatorId = request.userId
                    )
                )
            }
        }
    }
}