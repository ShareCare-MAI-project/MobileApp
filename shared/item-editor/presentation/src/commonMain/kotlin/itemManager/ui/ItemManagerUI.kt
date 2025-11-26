package itemManager.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import foundation.scrollables.VerticalScrollableBox
import itemManager.components.ItemManagerComponent
import itemManager.ui.sections.AIHelpSection
import itemManager.ui.sections.CreateButtonSection
import itemManager.ui.sections.DefaultInformationSection
import itemManager.ui.sections.DeliveryTypesSection
import itemManager.ui.sections.ItemImagesSection
import kotlinx.coroutines.Dispatchers
import utils.SpacerV
import utils.fastBackground
import view.consts.Paddings

@OptIn(
    ExperimentalFoundationApi::class
)
@Composable
internal fun ItemManagerUI(
    component: ItemManagerComponent
) {
    val createItemResult by component.createItemResult.collectAsState()

    val windowInsets = WindowInsets.safeContent.exclude(WindowInsets.ime)
    val safeContentPaddings = windowInsets.asPaddingValues()
    val topPadding = safeContentPaddings.calculateTopPadding()
    var scaffoldMaxTopPadding by remember { mutableStateOf(Paddings.small) }

    val hazeState = rememberHazeState()
    val scrollState = rememberScrollState()

    val images by component.photoTakerComponent.pickedPhotos.collectAsState(Dispatchers.Main.immediate)


    val title = component.title
    val description = component.description
    val itemCategory by component.itemCategory.collectAsState(Dispatchers.Main.immediate)
    val pickedDeliveryTypes by component.deliveryTypes.collectAsState(Dispatchers.Main.immediate)


    Scaffold(
        Modifier.fillMaxSize().imePadding(),
        topBar = {
            TopBar(
                isVisible = !scrollState.canScrollBackward,
                topPadding = topPadding, hazeState, component.closeFlow
            )
        }
    ) { scaffoldPaddings ->
        scaffoldMaxTopPadding =
            maxOf(scaffoldMaxTopPadding, scaffoldPaddings.calculateTopPadding())
        VerticalScrollableBox(
            modifier = Modifier.fillMaxSize()
                .fastBackground(colorScheme.background)
                .hazeSource(hazeState),
            windowInsets = windowInsets,
            scrollState = scrollState,
            bottomPaddingExtraHeight = Paddings.enormous
        ) {
            Column(Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                SpacerV(scaffoldMaxTopPadding - topPadding + Paddings.medium)
                ItemImagesSection(
                    images = images,
                    onAddButtonClick = {
                        if (!createItemResult.isLoading()) {
                            component.openPhotoTakerComponent()
                        }
                    },
                    onDeleteClick = {
                        if (!createItemResult.isLoading()) {
                            component.photoTakerComponent.deletePhoto(it)
                        }
                    }
                )

                SpacerV(Paddings.medium)

                AIHelpSection(images.isNotEmpty()) {
                    // TODO
                }

                SpacerV(Paddings.medium)
                DefaultInformationSection(
                    titleState = title,
                    descState = description,
                    itemCategory = itemCategory,
                    readOnly = createItemResult.isLoading()
                ) {
                    component.updateItemCategory(it)
                }
                SpacerV(Paddings.medium)
                DeliveryTypesSection(pickedDeliveryTypes) { item ->
                    component.updateDeliveryType(item)
                }
                SpacerV(Paddings.medium)
                CreateButtonSection(
                    enabled = title.text.isNotBlank()
                            && description.text.isNotBlank()
                            && images.isNotEmpty()
                            && itemCategory != null
                            && pickedDeliveryTypes.isNotEmpty(),
                    isLoading = createItemResult.isLoading()
                ) {
                    component.createItem()
                }

            }


        }
    }
}