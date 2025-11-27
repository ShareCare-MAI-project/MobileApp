package repositories

import entities.ShareCareItems
import kotlinx.coroutines.flow.Flow
import network.NetworkState

interface ShareCareRepository {
    fun fetchItems(): Flow<NetworkState<ShareCareItems>>
}