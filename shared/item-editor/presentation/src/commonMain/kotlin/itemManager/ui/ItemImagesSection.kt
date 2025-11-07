package itemManager.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontWeight
import common.ImagesRow
import utils.SpacerV
import view.consts.Paddings


@Composable
internal fun ItemImagesSection(
    images: List<ImageBitmap>,
    onAddButtonClick: () -> Unit,
    onDeleteClick: (ImageBitmap) -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            "Фотографии",
            fontWeight = FontWeight.Medium,
            style = typography.headlineSmall,
            maxLines = 1,
            autoSize = TextAutoSize.StepBased(maxFontSize = typography.headlineSmall.fontSize),
            modifier = Modifier.padding(horizontal = Paddings.horizontalListPadding)
        )
        SpacerV(Paddings.small)
        ImagesRow(
            addButton = onAddButtonClick,
            images = images,
            onDeleteClick = { onDeleteClick(it) },
            isReversedNumeric = false
        )
    }
}