package itemManager.ui.sections

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import entities.enums.ItemCategory
import itemManager.components.title
import utils.SpacerV
import view.consts.Paddings
import widgets.SectionTitle
import widgets.textField.SurfaceTextField
import widgets.textField.SurfaceTextFieldDefaults


@Composable
internal fun DefaultInformationSection(
    titleState: TextFieldState,
    descState: TextFieldState,
    itemCategory: ItemCategory?,
    readOnly: Boolean,
    onItemCategoryClick: (ItemCategory) -> Unit
) {
    SectionTitle("Информация")

    SpacerV(Paddings.small)
    DefaultInformationTextField(
        placeholderText = "Название",
        state = titleState,
        imeAction = ImeAction.Next,
        readOnly = readOnly
    )

    SpacerV(Paddings.small)
    DefaultInformationTextField(
        placeholderText = "Описание",
        state = descState,
        imeAction = ImeAction.Done,
        readOnly = readOnly
    )
    SpacerV(Paddings.small)

    CategoryTextField(itemCategory) {
        onItemCategoryClick(it)
    }

}

@Composable
private fun DefaultInformationTextField(
    paddingValues: PaddingValues = PaddingValues(horizontal = Paddings.horizontalListPadding),
    placeholderText: String,
    imeAction: ImeAction,
    state: TextFieldState,
    readOnly: Boolean = false,
    textFieldModifier: Modifier = SurfaceTextFieldDefaults.textFieldModifier
) {
    SurfaceTextField(
        state = state,
        placeholderText = placeholderText,
        modifier = Modifier.fillMaxWidth(),
        paddings = paddingValues,
        textStyle = typography.bodyLarge,
        imeAction = imeAction,
        readOnly = readOnly,
        textFieldModifier = textFieldModifier
    )
}

@Composable
private fun CategoryTextField(
    itemCategory: ItemCategory?,
    onClick: (ItemCategory) -> Unit
) {
    val state = TextFieldState(itemCategory?.title ?: "")
    var expanded by remember { mutableStateOf(false) }


    // rounded corners for dropdownmenu
    MaterialExpressiveTheme(
        shapes = shapes.copy(extraSmall = shapes.extraLarge)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = it
            },
            modifier = Modifier.padding(horizontal = Paddings.horizontalListPadding)
        ) {
            DefaultInformationTextField(
                placeholderText = "Категория",
                imeAction = ImeAction.Done,
                state = state,
                readOnly = true,
                paddingValues = PaddingValues.Zero,
                textFieldModifier = SurfaceTextFieldDefaults.textFieldModifier.menuAnchor(
                    ExposedDropdownMenuAnchorType.PrimaryNotEditable
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                ItemCategory.entries.forEach { itemCategory ->
                    DropdownMenuItem(
                        text = { Text(itemCategory.title) },
                        onClick = {
                            onClick(itemCategory)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}