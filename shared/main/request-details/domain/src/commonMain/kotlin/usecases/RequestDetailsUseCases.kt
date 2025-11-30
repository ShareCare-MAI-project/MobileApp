package usecases

import repositories.RequestDetailsRepository
import usecases.requestDetails.CreateRequestUseCase

class RequestDetailsUseCases(
    repository: RequestDetailsRepository
) {
    val createRequest = CreateRequestUseCase(repository)
}