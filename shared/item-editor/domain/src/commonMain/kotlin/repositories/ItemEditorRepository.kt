package repositories

import entities.Item
import kotlinx.coroutines.flow.Flow
import network.NetworkState

interface ItemEditorRepository {
    fun createItem(item: Item): Flow<NetworkState<Unit>>
}