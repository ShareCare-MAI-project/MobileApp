package usecases.auth

import repositories.AuthRepository

class RegisterUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke(name: String, telegram: String) = repository.register(
        name = name, telegram = telegram
    )
}