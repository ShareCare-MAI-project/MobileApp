package repositories

import entities.TakeItemResponse
import kotlinx.coroutines.flow.Flow
import network.NetworkState

interface ItemDetailsRepository {
    fun takeItem(itemId: String): Flow<NetworkState<TakeItemResponse>>
    fun denyItem(itemId: String): Flow<NetworkState<Unit>>
}