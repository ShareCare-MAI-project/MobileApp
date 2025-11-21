package repositories

import ktor.AuthRemoteDataSource

class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource,
) : AuthRepository {
    override fun requestCode(phone: String) = remoteDataSource.requestCode(phone)

}