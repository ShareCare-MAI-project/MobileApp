package itemDetails.ui.bottomSheet.sections

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import utils.SpacerV
import view.consts.Paddings
import widgets.LocationSection

@Composable
fun QuickInfoSection(
    title: String,
    location: String
) {
    Text(
        text = title,
        textAlign = TextAlign.Center,
        style = typography.headlineLargeEmphasized,
        modifier = Modifier
            .padding(horizontal = Paddings.medium),
        fontWeight = FontWeight.Medium
    )
    SpacerV(Paddings.small)
    LocationSection(modifier = Modifier.fillMaxWidth(), location = location)

}