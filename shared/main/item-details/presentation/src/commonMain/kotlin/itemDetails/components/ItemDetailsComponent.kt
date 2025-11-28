package itemDetails.components

import common.detailsInterfaces.DetailsComponent

interface ItemDetailsComponent: DetailsComponent {
    val images: List<String>
}