package requestDetails.components

import common.detailsInterfaces.DetailsComponent

interface RequestDetailsComponent: DetailsComponent {
    val onBackClick: () -> Unit
}