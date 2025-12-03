package repositories

import entities.Item
import kotlinx.coroutines.flow.Flow
import network.NetworkState

interface ItemEditorRepository {
    fun createItem(item: Item): Flow<NetworkState<Unit>>
    fun updateItem(item: Item, itemId: String): Flow<NetworkState<Unit>>
}