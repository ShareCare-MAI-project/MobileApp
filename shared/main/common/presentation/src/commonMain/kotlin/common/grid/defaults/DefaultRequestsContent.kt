package common.grid.defaults

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.Modifier
import common.detailsInterfaces.DetailsConfig
import common.grid.ContentType
import common.grid.TransitionColumnHeader
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
        contentType = contentType
    )
    items(
        items = requests,
        key = { it.id },
        contentType = { contentType }) { request ->
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