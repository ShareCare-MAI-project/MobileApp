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

}