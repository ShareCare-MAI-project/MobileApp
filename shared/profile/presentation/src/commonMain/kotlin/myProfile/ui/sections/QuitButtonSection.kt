package myProfile.ui.sections

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import widgets.expressiveList.ExpressiveListItem
import widgets.expressiveList.ExpressiveVerticalListItem

@Composable
internal fun QuitButtonSection(
    onClick: () -> Unit
) {
    val quit = ExpressiveListItem(
        icon = Icons.AutoMirrored.Rounded.Logout,
        text = "Выйти из аккаунта",
        containerColor = colorScheme.errorContainer,
        onClick = onClick
    )

    ExpressiveVerticalListItem(quit, modifier = Modifier.fillMaxWidth())
}