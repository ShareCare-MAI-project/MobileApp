package requestDetails.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import common.requestCard.RequestCardDefaults
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import requestDetails.components.RequestDetailsComponent
import requestDetails.ui.sections.ButtonSection
import requestDetails.ui.sections.DeliveryTypesSection
import requestDetails.ui.sections.TextFieldsSection
import utils.SpacerV
import view.consts.Paddings

@OptIn(
    ExperimentalHazeMaterialsApi::class,
    ExperimentalSharedTransitionApi::class, ExperimentalComposeUiApi::class
)//, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.RequestDetailsUI(
    component: RequestDetailsComponent
) {

    val category by component.category.collectAsState()
    val deliveryTypes by component.deliveryTypes.collectAsState()

    val createRequestResult by component.createRequestResult.collectAsState()

    val isEditing = !component.isCreating

    Dialog(
        onDismissRequest = component.onBackClick
    ) {
        Surface(Modifier.fillMaxWidth(), shape = RequestCardDefaults.cardShape) {
            Column(
                Modifier
                    .padding(top = Paddings.semiMedium, bottom = Paddings.medium)
                    .verticalScroll(rememberScrollState())
            ) {
                TextFieldsSection(
                    requestTextState = component.requestText,
                    category = category,
                    isCreationMode = component.isEditable,
                    isLoading = createRequestResult.isLoading()
                ) { component.updateCategory(it) }
                SpacerV(Paddings.semiMedium)

                DeliveryTypesSection(
                    deliveryTypes = deliveryTypes,
                    isEditable = component.isEditable
                ) { component.updateDeliveryType(it) }


                SpacerV(Paddings.semiMedium)
                ButtonSection(
                    isEditable = component.isEditable,
                    isEditing = isEditing,
                    requestText = component.requestText.text.toString(),
                    category = category,
                    deliveryTypes = deliveryTypes,
                    initialText = component.initialText,
                    initialDeliveryTypes = component.initialDeliveryTypes,
                    initialCategory = component.initialCategory,
                    isLoading = createRequestResult.isLoading(),
                    createOrEditRequest = component::createOrEditRequest,
                    onAcceptClick = {}
                )
            }
        }
    }
}