package usecases

import repositories.ItemDetailsRepository
import usecases.itemDetails.DenyItemUseCase
import usecases.itemDetails.TakeItemUseCase

class ItemDetailsUseCases(
    repository: ItemDetailsRepository
) {
    val takeItem = TakeItemUseCase(repository)
    val denyItem = DenyItemUseCase(repository)
}