package usecases

import repositories.FindHelpRepository
import usecases.findHelp.FetchBasicUseCase

class FindHelpUseCases(
    private val repository: FindHelpRepository
) {
    val fetchBasic = FetchBasicUseCase(repository)
}