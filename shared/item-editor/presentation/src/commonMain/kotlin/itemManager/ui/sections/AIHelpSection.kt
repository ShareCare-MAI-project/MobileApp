package itemManager.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import utils.SpacerH
import utils.SpacerV
import view.consts.Paddings

@Composable
fun AIHelpSection(
    isShown: Boolean,
    onClick: () -> Unit
) {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column {
            AnimatedVisibility(isShown) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TextButton(
                        onClick = onClick,
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = colorScheme.primaryContainer.copy(
                                alpha = .2f
                            )
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Rounded.AutoAwesome, null)
                            SpacerH(Paddings.small)
                            Text("Помощь от ИИ")
                        }
                    }
                    SpacerV(Paddings.semiSmall)
                    Text(
                        "ИИ сгенерирует описание предмета",
                        style = typography.bodySmall,
                        color = colorScheme.onBackground.copy(alpha = .7f)
                    )
                }
            }
        }
        Column {
            AnimatedVisibility(!isShown) {
                SpacerV(Paddings.small)
                Text(
                    "Добавьте фотографию, чтобы увидеть some magic ✨",
                    style = typography.bodySmall,
                    color = colorScheme.onBackground.copy(alpha = .7f)
                )
            }
        }
    }
}