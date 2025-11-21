package usecases

import repositories.AuthRepository
import usecases.auth.RequestCodeUseCase

class AuthUseCases(
    repository: AuthRepository
) {
    val requestCode = RequestCodeUseCase(repository)
}