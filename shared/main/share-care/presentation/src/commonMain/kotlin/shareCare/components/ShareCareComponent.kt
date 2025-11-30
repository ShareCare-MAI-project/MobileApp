package shareCare.components

import common.detailsInterfaces.DetailsConfig
import entities.ShareCareItems
import kotlinx.coroutines.flow.StateFlow
import network.NetworkState

interface ShareCareComponent {

    val items: StateFlow<NetworkState<ShareCareItems>>

    fun fetchItems()

    val openDetails: (cfg: DetailsConfig) -> Unit

    val query: StateFlow<String>
    fun onSearch(query: String)
}