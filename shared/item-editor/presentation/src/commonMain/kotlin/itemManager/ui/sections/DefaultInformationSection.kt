package itemManager.ui.sections

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import view.consts.Paddings
import widgets.SurfaceTextField

@Composable
internal fun DefaultTextFieldSection(
    placeholderText: String,
    imeAction: ImeAction,
    state: TextFieldState,
) {
    SurfaceTextField(
        state = state,
        placeholderText = placeholderText,
        modifier = Modifier.fillMaxWidth(),
        paddings = PaddingValues(horizontal = Paddings.horizontalListPadding),
        textStyle = typography.bodyLarge,
        imeAction = imeAction
    )
}