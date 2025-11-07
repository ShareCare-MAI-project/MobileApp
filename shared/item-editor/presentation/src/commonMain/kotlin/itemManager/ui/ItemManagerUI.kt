package itemManager.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import foundation.scrollables.VerticalScrollableBox
import itemManager.components.ItemManagerComponent
import kotlinx.coroutines.Dispatchers
import utils.SpacerV
import utils.fastBackground
import view.consts.Paddings

@Composable
internal fun ItemManagerUI(
    component: ItemManagerComponent
) {
    val windowInsets = WindowInsets.safeContent
    val safeContentPaddings = windowInsets.asPaddingValues()
    val topPadding = safeContentPaddings.calculateTopPadding()

    val hazeState = rememberHazeState()

    val images by component.photoTakerComponent.pickedPhotos.collectAsState(Dispatchers.Main.immediate)

    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            TopBar(topPadding = topPadding, hazeState, component.closeFlow)
        }
    ) { scaffoldPaddings ->
        VerticalScrollableBox(
            modifier = Modifier.fillMaxSize().fastBackground(colorScheme.background)
                .hazeSource(hazeState),
            windowInsets = windowInsets
        ) {
            Column(Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                SpacerV(scaffoldPaddings.calculateTopPadding() - topPadding + Paddings.medium)
                ItemImagesSection(
                    images = images,
                    onAddButtonClick = { component.openPhotoTakerComponent() },
                    onDeleteClick = { component.photoTakerComponent.deletePhoto(it) }
                )

                SpacerV(Paddings.small)

                AIHelpSection(images.isNotEmpty()) {
                    // TODO
                }

                



            }

        }
    }
}