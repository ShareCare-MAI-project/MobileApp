package shareCare.components

import common.detailsInterfaces.DetailsConfig
import common.search.SearchData
import entities.ShareCareItems
import kotlinx.coroutines.flow.StateFlow
import network.NetworkState

interface ShareCareComponent {


    fun denyItem(itemId: String)


    val items: StateFlow<NetworkState<ShareCareItems>>

    fun fetchItems()

    val openDetails: (cfg: DetailsConfig) -> Unit

    val searchData: StateFlow<SearchData>
    fun onQueryChange(query: String)
    fun onSearch()
}