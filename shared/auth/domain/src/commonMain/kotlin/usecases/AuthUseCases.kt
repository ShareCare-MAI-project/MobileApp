package usecases

import repositories.AuthRepository
import usecases.auth.FetchNameUseCase
import usecases.auth.FetchTokenUseCase
import usecases.auth.RegisterUseCase
import usecases.auth.RequestCodeUseCase
import usecases.auth.VerifyCodeUseCase

class AuthUseCases(
    repository: AuthRepository
) {
    val requestCode = RequestCodeUseCase(repository)

    val verifyCode = VerifyCodeUseCase(repository)

    val fetchToken = FetchTokenUseCase(repository)
    val fetchName = FetchNameUseCase(repository)

    val registerUser = RegisterUseCase(repository)
}