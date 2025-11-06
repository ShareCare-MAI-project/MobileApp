package itemEditorFlow.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import itemEditorFlow.components.ItemEditorFlowComponent
import photoTaker.ui.PhotoTakerUI

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun ItemEditorFlowUI(
    component: ItemEditorFlowComponent
) {

    val stack by component.stack.subscribeAsState()
    Children(
        stack = stack,
        modifier = Modifier.fillMaxSize().background(),
        animation = predictiveBackAnimation(
            backHandler = component.backHandler,
            fallbackAnimation = stackAnimation(),
            onBack = component::onBackClicked
        )
    ) {
        when (val child = it.instance) {
            is ItemEditorFlowComponent.Child.PhotoTakerChild ->
                PhotoTakerUI(child.photoTakerComponent)
        }
    }
}