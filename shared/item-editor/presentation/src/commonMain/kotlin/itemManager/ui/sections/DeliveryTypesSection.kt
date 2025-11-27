package itemManager.ui.sections

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import logic.enums.DeliveryType
import itemManager.ui.title
import utils.SpacerV
import view.consts.Paddings
import widgets.SectionTitle


@Composable
internal fun DeliveryTypesSection(
    pickedDeliveryTypes: List<DeliveryType>,
    onClick: (DeliveryType) -> Unit
) {
    val allDeliveryTypes = remember { DeliveryType.entries.toList() }


    SectionTitle("Способы доставки")
    SpacerV(Paddings.small)


    FlowRow(
        modifier = Modifier.fillMaxWidth().padding(horizontal = Paddings.horizontalListPadding),
        horizontalArrangement = Arrangement.spacedBy(Paddings.small, alignment = Alignment.Start),
        verticalArrangement = Arrangement.spacedBy(Paddings.semiSmall, alignment = Alignment.Top)
    ) {
        for (item in allDeliveryTypes) {
            AnimatedContent(
                item in pickedDeliveryTypes,
                transitionSpec = {
                    fadeIn(animationSpec = tween(220))
                        .togetherWith(fadeOut(animationSpec = tween(220)))
                },
            ) { selected ->
                DeliveryTypeItem(
                    selected = selected,
                    name = item.title
                ) {
                    onClick(item)
                }
            }
        }
    }

}

@Composable
private fun DeliveryTypeItem(
    selected: Boolean,
    name: String,
    onClick: () -> Unit,
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(
                name,
                modifier = Modifier
                    .padding(vertical = Paddings.small)
                    .basicMarquee(),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = FilterChipDefaults.elevatedFilterChipColors(
            selectedContainerColor = colorScheme.primaryContainer,
            selectedLabelColor = colorScheme.onPrimaryContainer
        ),
        shape = MaterialTheme.shapes.large
    )
}