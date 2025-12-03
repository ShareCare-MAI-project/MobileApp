package repositories

import dto.toDTO
import entities.Item
import kotlinx.coroutines.flow.Flow
import ktor.ItemEditorRemoteDataSource
import network.NetworkState

class ItemEditorRepositoryImpl(
    private val remoteDataSource: ItemEditorRemoteDataSource
) : ItemEditorRepository {
    override fun createItem(item: Item): Flow<NetworkState<Unit>> =
        remoteDataSource.createItem(item.toDTO())

    override fun updateItem(
        item: Item,
        itemId: String
    ): Flow<NetworkState<Unit>> = remoteDataSource.editItem(item.toDTO(), itemId)
}