package requestDetails.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import requestDetails.ui.sections.DeliveryTypesSection
import requestDetails.ui.sections.TextFieldsSection
import utils.SpacerV
import view.consts.Paddings
import widgets.sections.CreateButtonSection

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

    Dialog(
        onDismissRequest = component.onBackClick
    ) {
        Surface(Modifier.fillMaxWidth(), shape = RequestCardDefaults.cardShape) {
            Column(
                Modifier
                    .padding(top = Paddings.semiMedium, bottom = Paddings.medium)
            ) {
                TextFieldsSection(
                    requestTextState = component.requestText,
                    category = category,
                    isCreationMode = component.isCreationMode,

                    ) { component.updateCategory(it) }
                SpacerV(Paddings.semiMedium)

                DeliveryTypesSection(
                    deliveryTypes = deliveryTypes
                ) { component.updateDeliveryType(it) }


                if (component.isCreationMode) {
                    SpacerV(Paddings.medium)
                    CreateButtonSection(
                        enabled = component.requestText.text.isNotBlank() && category != null && deliveryTypes.isNotEmpty(),
                        isLoading = createRequestResult.isLoading(),
                        text = "Создать заявку"
                    ) {

                    }
                }
            }
        }
    }
}