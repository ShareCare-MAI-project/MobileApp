package widgets

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import logic.enums.DeliveryType
import utils.SpacerH
import utils.title
import view.consts.Paddings

data object DeliveryTypesPickerDefaults {
    val allDeliveryTypes = DeliveryType.entries.toList()
}

@Composable
fun DeliveryTypesPicker(
    pickedDeliveryTypes: List<DeliveryType>,
    allDeliveryTypes: List<DeliveryType> = DeliveryTypesPickerDefaults.allDeliveryTypes,
    modifier: Modifier = Modifier.fillMaxWidth().padding(horizontal = Paddings.listHorizontalPadding),
    isTransparent: Boolean = false,
    initSpacer: Dp = 0.dp,
    onClick: (DeliveryType) -> Unit
) {

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Paddings.small, alignment = Alignment.Start),
        verticalArrangement = Arrangement.spacedBy(Paddings.semiSmall, alignment = Alignment.Top)
    ) {
        SpacerH(initSpacer)
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
                    name = item.title,
                    isTransparent = isTransparent
                ) {
                    onClick(item)
                }
            }
        }

        SpacerH(initSpacer)
    }
}

@Composable
private fun DeliveryTypeItem(
    selected: Boolean,
    name: String,
    isTransparent: Boolean,
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
            selectedLabelColor = colorScheme.onPrimaryContainer,
            containerColor = colorScheme.surfaceContainerLow.copy(alpha = if (isTransparent) .7f else 1f)
        ),
        shape = MaterialTheme.shapes.large
    )
}