package ktor.user

import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import ktor.TokenProvider
import ktor.auth.models.responses.UserFullInfoResponse
import ktor.defaultGet
import network.NetworkState

class UserRemoteDataSource(
    private val hc: HttpClient,
    private val tokenProvider: TokenProvider
) {
    fun getCurrentUserInfo(): Flow<NetworkState<UserFullInfoResponse>> =
        hc.defaultGet(path = GET_CURRENT_USER_INFO_PATH, tokenProvider = tokenProvider)

    private companion object {
        const val PRE_PATH = "/user"
        const val GET_CURRENT_USER_INFO_PATH = "$PRE_PATH/me"
    }
}