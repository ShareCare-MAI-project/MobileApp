import ktor.TokenProvider
import repositories.AuthRepository
import usecases.auth.FetchTokenUseCase

// TokenProvider cуществует, чтобы пробрасывать токен в core модуль
// Поэтому я не могу использовать FetchTokenUseCase, т.к. это приведёт к CircularDependency
class TokenProviderImpl(val repository: AuthRepository): TokenProvider {
    override fun getToken(): String? {
        return FetchTokenUseCase(repository).invoke()
    }
}
class NullTokenProviderImpl(): TokenProvider {
    override fun getToken(): String? = null
}