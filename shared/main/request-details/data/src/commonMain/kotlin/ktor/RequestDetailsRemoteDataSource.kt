package ktor

import dto.RequestDTO
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import network.NetworkState

class RequestDetailsRemoteDataSource(
    private val hc: HttpClient,
    private val tokenProvider: TokenProvider
) {

    fun createRequest(request: RequestDTO): Flow<NetworkState<Unit>> =
        hc.defaultPost(CREATE_REQUEST_PATH, tokenProvider, body = request)


    private companion object {
        const val PRE_PATH = "/requests"
        const val CREATE_REQUEST_PATH = "$PRE_PATH/"
    }
}