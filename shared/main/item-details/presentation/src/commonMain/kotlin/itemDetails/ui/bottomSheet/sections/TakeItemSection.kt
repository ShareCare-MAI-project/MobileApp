package itemDetails.ui.bottomSheet.sections

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import utils.SpacerH
import utils.SpacerV
import view.consts.Paddings

@Composable
fun TakeItemSection(
    isOwner: Boolean,
    isRecipient: Boolean
) {
    if (!isOwner && !isRecipient) {
        SpacerV(Paddings.big)
        FloatingActionButton(
            onClick = {},
            containerColor = colorScheme.primary
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = Paddings.medium)
            ) {
                Icon(Icons.Rounded.AddBox, null)
                SpacerH(Paddings.semiSmall)
                Text("Забрать вещь")
            }
        }
        SpacerV(Paddings.big)
    }
}