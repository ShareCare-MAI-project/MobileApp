package usecases

import repositories.AuthRepository
import usecases.auth.RequestCodeUseCase
import usecases.auth.VerifyCodeUseCase

class AuthUseCases(
    repository: AuthRepository
) {
    val requestCode = RequestCodeUseCase(repository)

    val verifyCode = VerifyCodeUseCase(repository)
}