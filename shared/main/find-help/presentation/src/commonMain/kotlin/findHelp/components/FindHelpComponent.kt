package findHelp.components

import common.detailsInterfaces.DetailsConfig

interface FindHelpComponent {

    val openDetails: (cfg: DetailsConfig) -> Unit
}