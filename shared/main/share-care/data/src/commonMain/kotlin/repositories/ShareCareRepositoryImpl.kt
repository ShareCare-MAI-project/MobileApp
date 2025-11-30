package repositories

import entities.ShareCareItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ktor.ShareCareRemoteDataSource
import network.NetworkState

class ShareCareRepositoryImpl(
    private val remoteDataSource: ShareCareRemoteDataSource
) : ShareCareRepository {
    override fun fetchItems(): Flow<NetworkState<ShareCareItems>> = flow {
        remoteDataSource.fetchItems().collect { itemsResponse ->
            emit(
                itemsResponse.defaultWhen { response ->
                    NetworkState.Success(response.data.toDomain())
                }
            )
        }
    }.flowOn(Dispatchers.IO)

}