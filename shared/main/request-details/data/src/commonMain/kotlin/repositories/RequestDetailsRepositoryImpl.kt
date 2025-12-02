package repositories

import dto.toDTO
import entities.Request
import kotlinx.coroutines.flow.Flow
import ktor.RequestDetailsRemoteDataSource
import network.NetworkState

class RequestDetailsRepositoryImpl(
    val remoteDataSource: RequestDetailsRemoteDataSource
) : RequestDetailsRepository {
    override fun createRequest(request: Request): Flow<NetworkState<Unit>> =
        remoteDataSource.createRequest(request.toDTO())

    override fun editRequest(
        request: Request,
        requestId: String
    ): Flow<NetworkState<Unit>> =
        remoteDataSource.editRequest(request.toDTO(requestId))

    override fun deleteRequest(requestId: String): Flow<NetworkState<Unit>> =
        remoteDataSource.deleteRequest(requestId)


}