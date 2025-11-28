package requestDetails.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import common.requestCard.RequestCardDefaults
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import requestDetails.components.RequestDetailsComponent
import utils.SpacerV
import view.consts.Paddings
import widgets.DeliveryTypesPicker
import widgets.textField.CategoryTextField
import widgets.textField.SurfaceTextField

@OptIn(
    ExperimentalHazeMaterialsApi::class,
    ExperimentalSharedTransitionApi::class, ExperimentalComposeUiApi::class
)//, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.RequestDetailsUI(
    component: RequestDetailsComponent
) {
    Dialog(
        onDismissRequest = component.onBackClick
    ) {
        Surface(Modifier.fillMaxWidth(), shape = RequestCardDefaults.cardShape) {
            Column(
                Modifier
                    .padding(top = Paddings.semiMedium, bottom = Paddings.medium)
            ) {
                SurfaceTextField(
                    state = TextFieldState("Нужны штаны на зиму, рост 167"),
                    placeholderText = "Заявка",
                    paddings = PaddingValues(horizontal = Paddings.medium)
                )
                SpacerV(Paddings.small)
                CategoryTextField(null, modifier = Modifier.padding(horizontal = Paddings.medium)) {

                }
                SpacerV(Paddings.semiMedium)
                Text(
                    "Способы доставки",
                    modifier = Modifier.padding(start = Paddings.medium),
                    fontWeight = FontWeight.Medium
                )
                SpacerV(Paddings.semiSmall)
                DeliveryTypesPicker(
                    listOf(), modifier = Modifier.horizontalScroll(
                        rememberScrollState()
                    ),
                    initSpacer = Paddings.semiSmall
                ) {}
            }
        }
    }
}