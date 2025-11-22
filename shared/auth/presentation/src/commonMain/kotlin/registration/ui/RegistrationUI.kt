package registration.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.insert
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.NavigateNext
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import animations.NetworkButtonIconAnimation
import foundation.scrollables.VerticalScrollableBox
import icons.Telegram
import registration.components.RegistrationComponent
import utils.SpacerH
import utils.SpacerV
import utils.fastBackground
import view.consts.Paddings
import view.consts.Sizes.logoMaxSize
import widgets.textField.SurfaceTextField

@Composable
fun RegistrationUI(component: RegistrationComponent) {


    val windowInsets = WindowInsets.safeContent.exclude(WindowInsets.ime)

    VerticalScrollableBox(
        modifier = Modifier.fillMaxSize().fastBackground(colorScheme.background).imePadding(),
        windowInsets = windowInsets,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = Paddings.medium)
                .sizeIn(maxWidth = logoMaxSize)
        ) {
            Card(
                modifier = Modifier
                    .sizeIn(maxWidth = logoMaxSize, maxHeight = logoMaxSize)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                shape = shapes.extraLarge
            ) {}
            SpacerV(Paddings.medium)
            Text(
                "Регистрация",
                textAlign = TextAlign.Center,
                style = typography.headlineLargeEmphasized,
                fontWeight = FontWeight.Medium
            )
            SpacerV(Paddings.semiMedium)


            SurfaceTextField(
                state = TextFieldState(),
                placeholderText = "Ваше имя",
                icon = Icons.Rounded.Person,
                singleLine = true,
                imeAction = ImeAction.Next,
                inputTransformation = {
                    val text = this.asCharSequence()

                    if (text.any { !it.isLetter() && it != ' ' }) revertAllChanges()
                    else if (text.isNotEmpty()) {
                        val firstChar = text.first()
                        if (firstChar == ' ') revertAllChanges()
                        else replace(0, 1, firstChar.uppercase())
                    }
                }
            )
            SpacerV(Paddings.small)


            SurfaceTextField(
                state = TextFieldState(),
                placeholderText = "Телеграм @username",
                icon = Icons.Rounded.Telegram,
                singleLine = true,
                inputTransformation = {
                    val text = this.asCharSequence()
                    if (text.isNotBlank() && text.first() != '@') {
                        this.insert(0, "@")
                    }
                }
            )
            SpacerV(Paddings.small)

            Text(
                "Он будет использован для связи",
                style = typography.bodyMedium,
                color = colorScheme.onBackground.copy(alpha = .8f),
                textAlign = TextAlign.Center
            )

            SpacerV(Paddings.big)
            Button(
                enabled = true,
                onClick = {

                }
            ) {
                Text("Зарегистрироваться")
                SpacerH(Paddings.semiSmall)
                NetworkButtonIconAnimation(
                    icon = Icons.AutoMirrored.Rounded.NavigateNext,
                    isLoading = false
                )
            }
        }
    }
}