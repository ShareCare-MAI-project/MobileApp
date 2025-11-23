package repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ktor.AuthRemoteDataSource
import network.NetworkState
import settings.AuthLocalDataSource

class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AuthLocalDataSource,
) : AuthRepository {
    override fun requestCode(phone: String) = remoteDataSource.requestCode(phone)

    override fun verifyCode(
        phone: String,
        otp: String
    ): Flow<NetworkState<Boolean>> = flow {
        remoteDataSource.verifyCode(phone = phone, code = otp).collect { verifyCodeResponse ->
            emit(verifyCodeResponse.defaultWhen { response ->
                localDataSource.saveToken(response.data.token)
                println("SAVED: ${localDataSource.fetchToken()}")
                (NetworkState.Success(response.data.name == null))
            })
        }
    }.flowOn(Dispatchers.IO)

    override fun register(
        name: String,
        telegram: String
    ): Flow<NetworkState<Unit>> = flow {
        remoteDataSource.registerUser(name = name, telegram = telegram.removePrefix("@"))
            .collect { registerResponse ->
                emit(registerResponse.defaultWhen { response ->
                    localDataSource.saveName(name)
                    response
                })
            }
    }.flowOn(Dispatchers.IO)

    override fun fetchToken(): String? = localDataSource.fetchToken()

    override fun fetchName(): String? = localDataSource.fetchName()
}