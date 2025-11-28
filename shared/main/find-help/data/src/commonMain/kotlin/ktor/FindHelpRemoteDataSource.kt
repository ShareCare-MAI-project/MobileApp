package ktor

import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import ktor.models.response.FindHelpBasicResponse
import network.NetworkState

class FindHelpRemoteDataSource(
    private val hc: HttpClient,
    private val tokenProvider: TokenProvider
) {
    fun fetchBasic(): Flow<NetworkState<FindHelpBasicResponse>> =
        hc.defaultGet(path = FETCH_BASIC_PATH, tokenProvider)


    private companion object {
        const val PRE_PATH = "/findhelp"
        const val FETCH_BASIC_PATH = "$PRE_PATH/basic"
    }

}