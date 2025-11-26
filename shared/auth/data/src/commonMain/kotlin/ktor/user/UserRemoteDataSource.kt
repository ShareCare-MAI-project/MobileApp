package ktor.user

import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import ktor.auth.models.responses.UserFullInfoResponse
import ktor.defaultGet
import network.NetworkState

class UserRemoteDataSource(
    private val hc: HttpClient
) {
    fun getCurrentUserInfo(): Flow<NetworkState<UserFullInfoResponse>> =
        hc.defaultGet(path = GET_CURRENT_USER_INFO_PATH)

    private companion object {
        const val PRE_PATH = "/user"
        const val GET_CURRENT_USER_INFO_PATH = "$PRE_PATH/me"
    }
}