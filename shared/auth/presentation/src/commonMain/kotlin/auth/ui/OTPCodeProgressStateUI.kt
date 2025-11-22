package auth.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Message
import androidx.compose.material.icons.automirrored.rounded.NavigateBefore
import androidx.compose.material.icons.automirrored.rounded.NavigateNext
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.SplitButtonDefaults
import androidx.compose.material3.SplitButtonLayout
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import auth.components.AuthComponent
import network.NetworkState
import utils.SpacerH
import utils.SpacerV
import view.consts.Paddings
import view.consts.Sizes
import widgets.textField.SurfaceTextField


@Composable
internal fun OTPCodeProgressStateUI(
    component: AuthComponent,
    onError: suspend (String) -> Unit
) {

    val verifyCodeResult by component.verifyCodeResult.collectAsState()

    LaunchedEffect(verifyCodeResult) {
        if (verifyCodeResult.isErrored()) {
            onError((verifyCodeResult as NetworkState.Error).prettyPrint)
        }
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
                onClick = {
                    component.onVerifyCodeClick()
                },
                enabled = component.OTPCode.text.length == 4
                        && component.OTPCode.text.all { it.isDigit() }
            ) {
                SpacerH(Paddings.semiSmall)
                Text("Далее")
                SpacerH(Paddings.semiSmall)
                if (!verifyCodeResult.isLoading())
                    Icon(Icons.AutoMirrored.Rounded.NavigateNext, null)
                else
                    CircularProgressIndicator(Modifier.size(Sizes.iconSize))
            }
        }
    )
}