package usecases.auth

import repositories.AuthRepository

class FetchNameUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.fetchName()
}