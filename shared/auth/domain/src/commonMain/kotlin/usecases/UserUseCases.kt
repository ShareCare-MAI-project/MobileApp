package usecases

import repositories.UserRepository
import usecases.user.FetchNameUseCase
import usecases.user.UpdateCurrentUserUseCase

class UserUseCases(
    repository: UserRepository
) {
    val fetchName = FetchNameUseCase(repository)

    val updateCurrentUserInfo = UpdateCurrentUserUseCase(repository)
}