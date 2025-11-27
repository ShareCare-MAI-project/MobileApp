package shareCare.components

import entities.ShareCareItems
import kotlinx.coroutines.flow.StateFlow
import network.NetworkState

interface ShareCareComponent {

    val items: StateFlow<NetworkState<ShareCareItems>>

    fun fetchItems()

}