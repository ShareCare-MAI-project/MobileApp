package repositories

import entities.FindHelpBasic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ktor.FindHelpRemoteDataSource
import network.NetworkState

class FindHelpRepositoryImpl(
    private val remoteDataSource: FindHelpRemoteDataSource
) : FindHelpRepository {
    override fun fetchBasic(): Flow<NetworkState<FindHelpBasic>> = flow {
        remoteDataSource.fetchBasic().collect { itemsResponse ->
            emit(
                itemsResponse.defaultWhen { response ->
                    NetworkState.Success(response.data.toDomain())
                }
            )
        }
    }.flowOn(Dispatchers.IO)

}