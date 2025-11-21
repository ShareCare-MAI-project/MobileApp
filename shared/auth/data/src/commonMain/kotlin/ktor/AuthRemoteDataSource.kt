package ktor

import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import ktor.bodies.RequestCodeBody
import network.NetworkState


class AuthRemoteDataSource(
    private val hc: HttpClient
) {
    fun requestCode(phone: String): Flow<NetworkState<Unit>> =
        hc.defaultPost(path = REQUEST_CODE_PATH, body = RequestCodeBody(phone = phone))

    private companion object {
        const val PRE_PATH = "/auth"

        const val REQUEST_CODE_PATH = "$PRE_PATH/request-otp"
    }
}