package compose

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import animations.iosSlide
import auth.ui.AuthUI
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import components.RootComponent
import itemEditorFlow.ui.ItemEditorFlowUI
import mainFlow.ui.MainFlowUI
import profileFlow.ui.ProfileFlowUI
import registration.ui.RegistrationUI
import ui.HelloUI

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun SharedTransitionScope.RootContent(
    component: RootComponent
) {

    val stack by component.stack.subscribeAsState()





    Surface(
        Modifier.fillMaxSize()
    ) {
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
                is RootComponent.Child.AuthChild -> AuthUI(child.authComponent)
                is RootComponent.Child.RegistrationChild -> RegistrationUI(child.registrationComponent)
                is RootComponent.Child.MainFlowChild -> MainFlowUI(child.mainFlowComponent)
                is RootComponent.Child.ItemEditorChild -> ItemEditorFlowUI(child.itemEditorComponent)
                is RootComponent.Child.ProfileFlowChild -> ProfileFlowUI(child.profileFlowComponent)
            }
        }
    }
}