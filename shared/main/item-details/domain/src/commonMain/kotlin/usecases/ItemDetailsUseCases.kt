package usecases

import repositories.ItemDetailsRepository
import usecases.itemDetails.TakeItemUseCase

class ItemDetailsUseCases(
    repository: ItemDetailsRepository
) {
    val takeItem = TakeItemUseCase(repository)
}