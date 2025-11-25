package mainFlow.ui.topBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import dev.chrisbanes.haze.HazeState
import widgets.glass.GlassCard

@Composable
internal fun AvatarButton(
    hazeState: HazeState,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = Modifier.aspectRatio(1f).clickable {
            onClick()
        },
        hazeState = hazeState,
        shape = CircleShape,
        isReversedProgressive = true,
        tint = colorScheme.tertiaryContainer.copy(alpha = .3f),
        borderColor = colorScheme.tertiaryContainer.copy(alpha = .15f)
    ) {
//        if (!isSearchTextFieldFocused) {
        Box(Modifier.matchParentSize(), contentAlignment = Alignment.Center) {
            Icon(
                Icons.Rounded.Person,
                modifier = Modifier.matchParentSize().scale(1.2f),
                contentDescription = null
            )
        }
//        }
    }
}