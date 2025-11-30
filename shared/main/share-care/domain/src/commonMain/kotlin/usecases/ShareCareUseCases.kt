package usecases

import repositories.ShareCareRepository
import usecases.sharecare.FetchShareCareItemsUseCase

class ShareCareUseCases(
    private val repository: ShareCareRepository
) {
    val fetchItems = FetchShareCareItemsUseCase(repository)
}