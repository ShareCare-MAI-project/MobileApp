package common.grid.defaults

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.detailsInterfaces.DetailsConfig
import common.grid.ContentType
import common.requestCard.RequestCard
import entity.RequestResponse

@Suppress("FunctionName")
fun LazyGridScope.DefaultRequestsContent(
    requests: List<RequestResponse>,
    contentType: ContentType,
    sharedTransitionScope: SharedTransitionScope,
    filters: @Composable (() -> Unit)? = null,
    onCardClicked: (DetailsConfig.RequestDetailsConfig) -> Unit
) {
    DefaultGridContent(
        items = requests,
        key = { it.id },
        contentType = contentType,
        filters = filters
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