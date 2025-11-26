package myProfile.ui.sections

import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import utils.SpacerV
import utils.getCoolPrimary
import view.consts.Paddings
import widgets.SectionTitle

@Composable
internal fun FontSizeSection() {


    val activeColor = getCoolPrimary()
    val colors = SliderDefaults.colors(
        thumbColor = activeColor,
        activeTrackColor = activeColor,
        inactiveTrackColor = colorScheme.surfaceContainerHighest
    )
    SectionTitle("Размер шрифта", 0.dp)
    SpacerV(Paddings.small)

    var value by remember { mutableStateOf(1f) }
    Slider(
        value = value,
        onValueChange = {
            value = it
        },
        valueRange = 0.75f..1.25f,
        steps = 9,
        colors = colors,
        track = { sliderState ->
            SliderDefaults.Track(
                sliderState = sliderState,
                modifier = Modifier.height(25.dp),
                trackCornerSize = 10.dp,
                colors = colors
            )
        },
    )
}