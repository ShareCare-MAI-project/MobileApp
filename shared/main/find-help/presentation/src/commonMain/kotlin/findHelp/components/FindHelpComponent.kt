package findHelp.components

import common.detailsInterfaces.DetailsConfig
import entities.FindHelpBasic
import kotlinx.coroutines.flow.StateFlow
import network.NetworkState

interface FindHelpComponent {
    val basic: StateFlow<NetworkState<FindHelpBasic>>

    fun fetchBasic()


    val openDetails: (cfg: DetailsConfig) -> Unit

    val query: StateFlow<String>
    fun onSearch(query: String)
}