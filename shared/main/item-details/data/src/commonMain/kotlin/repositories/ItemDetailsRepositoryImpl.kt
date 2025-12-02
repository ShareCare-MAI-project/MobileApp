package repositories

import entities.TakeItemResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ktor.ItemDetailsRemoteDataSource
import network.NetworkState

class ItemDetailsRepositoryImpl(
    private val remoteDataSource: ItemDetailsRemoteDataSource
) : ItemDetailsRepository {
    override fun takeItem(itemId: String): Flow<NetworkState<TakeItemResponse>> =
        flow {
            remoteDataSource.takeItem(itemId).collect { takeItemResponse ->
                emit(
                    takeItemResponse.defaultWhen { response ->
                        NetworkState.Success(
                            response.data.toDomain()
                        )
                    }
                )
            }
        }.flowOn(Dispatchers.IO)

    override fun denyItem(itemId: String): Flow<NetworkState<Unit>> =
        remoteDataSource.denyItem(itemId)

}