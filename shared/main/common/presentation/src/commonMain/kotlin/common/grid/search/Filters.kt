package common.grid.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.grid.search.sections.CategoryPicker
import common.grid.search.sections.PreviewSection
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import utils.SpacerV
import view.consts.Paddings
import widgets.DeliveryTypesPicker
import widgets.sections.SmallSectionTitle

@Composable
fun Filters(
    location: String = "Москва, Сокол",
    deliveryTypes: List<DeliveryType>,
    category: ItemCategory?
) {

    var isExpanded by remember { mutableStateOf(false) }
    val countOfActiveFilters = 0 +
            (if (category != null) 1 else 0) +
            (if (deliveryTypes.isNotEmpty()) 1 else 0)

    Column(Modifier.padding(horizontal = Paddings.semiSmall)) { // same as TransitionColumnHeader
        PreviewSection(
            location = location,
            countOfActiveFilters = countOfActiveFilters,
            isExpanded = isExpanded
        ) { isExpanded = !isExpanded }
        AnimatedVisibility(isExpanded) {
            Column {
                SpacerV(Paddings.semiSmall)
                SmallSectionTitle(text = "Способ доставки")
                DeliveryTypesPicker(
                    modifier = Modifier.fillMaxWidth(),
                    pickedDeliveryTypes = deliveryTypes,
                    onOtherClick = {}
                ) {}

                SpacerV(Paddings.semiSmall)
                SmallSectionTitle(text = "Категория")
                CategoryPicker(
                    modifier = Modifier.fillMaxWidth(),
                    pickedCategory = category,
                    onOtherClick = {}
                ) {}
                SpacerV(Paddings.small)
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(.6f).align(Alignment.CenterHorizontally),
                    thickness = 1.dp
                )
            }
        }
    }
}