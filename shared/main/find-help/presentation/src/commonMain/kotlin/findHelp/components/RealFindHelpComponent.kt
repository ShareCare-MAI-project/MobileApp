package findHelp.components

import com.arkivanov.decompose.ComponentContext
import common.detailsInterfaces.DetailsConfig

class RealFindHelpComponent(
    componentContext: ComponentContext,
    override val openDetails: (cfg: DetailsConfig) -> Unit
) : FindHelpComponent, ComponentContext by componentContext {

}