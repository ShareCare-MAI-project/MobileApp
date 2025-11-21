package auth.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Message
import androidx.compose.material.icons.automirrored.rounded.NavigateBefore
import androidx.compose.material.icons.automirrored.rounded.NavigateNext
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.SplitButtonDefaults
import androidx.compose.material3.SplitButtonLayout
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import auth.components.AuthComponent
import utils.SpacerH
import utils.SpacerV
import view.consts.Paddings
import widgets.textField.SurfaceTextField


@Composable
internal fun OTPCodeProgressStateUI(
    component: AuthComponent
) {

    //validate otp-code
    LaunchedEffect(component.OTPCode.text) {

    }

    SurfaceTextField(
        state = component.OTPCode,
        placeholderText = "Код",
        icon = Icons.AutoMirrored.Rounded.Message,
        singleLine = true,
        keyboardType = KeyboardType.NumberPassword,
        inputTransformation = {
            val text = asCharSequence()
            val shouldRevert =
                text.length > 4 || text.any { !it.isDigit() }

            if (shouldRevert) revertAllChanges()
        }
    )
    SpacerV(Paddings.small)

    Text(
        "Не пришёл код? Введите \"1111\" (MVP)",
        style = typography.bodyMedium,
        color = colorScheme.onBackground.copy(alpha = .8f),
        textAlign = TextAlign.Center
    )

    SpacerV(Paddings.big)

    SplitButtonLayout(
        leadingButton = {
            SplitButtonDefaults.LeadingButton(
                onClick = {
                    component.onBackClick()
                }
            ) {
                Icon(Icons.AutoMirrored.Rounded.NavigateBefore, null)
            }
        },
        trailingButton = {
            SplitButtonDefaults.TrailingButton(
                onClick = {}
            ) {
                SpacerH(Paddings.semiSmall)
                Text("Далее")
                SpacerH(Paddings.semiSmall)
                Icon(Icons.AutoMirrored.Rounded.NavigateNext, null)
            }
        }
    )
}