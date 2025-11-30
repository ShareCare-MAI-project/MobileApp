package compose

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.arkivanov.decompose.ExperimentalDecomposeApi
import components.RootComponent
import compose.alerts.AlertsUI
import dev.chrisbanes.haze.LocalHazeStyle
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import view.theme.AppTheme

@OptIn(ExperimentalHazeMaterialsApi::class, ExperimentalSharedTransitionApi::class,
    ExperimentalDecomposeApi::class
)
@Composable
fun RootUI(
    component: RootComponent
) {
    SharedTransitionLayout {

        AppTheme {

            CompositionLocalProvider(
                LocalHazeStyle provides HazeMaterials.regular(colorScheme.background)
            ) {
                RootContent(component)
                AlertsUI()
            }
        }
    }
}
