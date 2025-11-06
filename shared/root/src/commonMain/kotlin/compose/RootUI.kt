package compose

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import animations.iosSlide
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import components.RootComponent
import dev.chrisbanes.haze.LocalHazeStyle
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import itemEditorFlow.ui.ItemEditorFlowUI
import mainFlow.ui.MainFlowUI
import ui.HelloUI
import view.theme.AppTheme

@OptIn(ExperimentalHazeMaterialsApi::class, ExperimentalSharedTransitionApi::class,
    ExperimentalDecomposeApi::class
)
@Composable
fun RootUI(
    component: RootComponent
) {
    val stack by component.stack.subscribeAsState()
    SharedTransitionLayout {

        AppTheme {

            CompositionLocalProvider(
                LocalHazeStyle provides HazeMaterials.regular(colorScheme.background)
            ) {

                Surface(Modifier.fillMaxSize()) {
                    Children(
                        stack = stack,
                        animation = predictiveBackAnimation(
                            backHandler = component.backHandler,
                            fallbackAnimation = stackAnimation(iosSlide()),
                            onBack = component::onBackClicked
                        )
                    ) {

                        when (val child = it.instance) {
                            is RootComponent.Child.HelloChild -> HelloUI(child.helloComponent)
                            is RootComponent.Child.MainFlowChild -> MainFlowUI(child.mainFlowComponent)
                            is RootComponent.Child.ItemEditorChild -> ItemEditorFlowUI(child.itemEditorComponent)
                        }
                    }
                }
            }
        }
    }
}
