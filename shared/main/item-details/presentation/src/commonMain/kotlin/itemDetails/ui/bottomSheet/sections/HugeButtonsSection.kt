package itemDetails.ui.bottomSheet.sections

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import utils.SpacerV
import view.consts.Paddings
import widgets.expressiveList.ExpressiveHorizontalList
import widgets.expressiveList.ExpressiveListItem

@Composable
fun HugeButtonsSection(
    buttons: List<ExpressiveListItem>
) {
    if (buttons.isNotEmpty()) {
        ExpressiveHorizontalList(
            modifier = Modifier.fillMaxWidth(),
            items = buttons
        )
        SpacerV(Paddings.semiMedium)
    }
}




