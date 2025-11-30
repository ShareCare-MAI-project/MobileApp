package usecases

import repositories.ItemEditorRepository
import usecases.itemEditor.CreateItemUseCase

class ItemEditorUseCases(
    repository: ItemEditorRepository
) {
    val createItem = CreateItemUseCase(repository)
}