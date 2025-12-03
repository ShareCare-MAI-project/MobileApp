package usecases

import repositories.SettingsRepository
import usecases.settings.ChangeUsuallyIUseCase
import usecases.settings.FetchUsuallyIUseCase

class SettingsUseCases(
    repository: SettingsRepository
) {
    val changeUsuallyI = ChangeUsuallyIUseCase(repository)
    val fetchUsuallyI = FetchUsuallyIUseCase(repository)
}