package usecases

import repositories.ItemEditorRepository
import usecases.itemEditor.CreateItemUseCase
import usecases.itemEditor.UpdateItemUseCase

class ItemEditorUseCases(
    repository: ItemEditorRepository
) {
    val createItem = CreateItemUseCase(repository)
    val updateItem = UpdateItemUseCase(repository)
}