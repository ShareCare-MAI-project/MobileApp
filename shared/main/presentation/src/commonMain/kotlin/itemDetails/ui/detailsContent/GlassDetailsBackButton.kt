package itemDetails.ui.detailsContent

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import dev.chrisbanes.haze.HazeState
import view.consts.Paddings
import widgets.glass.GlassCard
import widgets.glass.GlassCardFunctions

@Composable
internal fun GlassDetailsBackButton(
    isVisible: Boolean,
    modifier: Modifier,
    hazeState: HazeState,
    onClick: () -> Unit
) {

    AnimatedVisibility(
        visible = isVisible,
        modifier = modifier,
        enter = fadeIn() + scaleIn(
            spring(
                stiffness = Spring.StiffnessLow,
                dampingRatio = Spring.DampingRatioMediumBouncy
            )
        ),
        exit = fadeOut() + scaleOut()
    ) {
        GlassCard(
            // Because of Padding...(about clip)
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    onClick()
                },
            hazeState = hazeState,
            shape = CircleShape,
            contentPadding = PaddingValues(Paddings.semiMedium),
            contentAlignment = Alignment.Center,
            tint = Color.Transparent,
            hazeTint = GlassCardFunctions.getHazeTintColor(
                tint = null,
                containerColor = colorScheme.primaryContainer,
                containerColorAlpha = .4f
            ),
            borderColor = GlassCardFunctions.getBorderColor(
                tint = null,
                containerColor = colorScheme.primaryContainer,
                containerColorAlpha = .1f
            ),
            contentColor = Color.White
        ) {
            Icon(Icons.AutoMirrored.Rounded.ArrowBackIos, contentDescription = null)
        }
    }
}