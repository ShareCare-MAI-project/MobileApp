package requestDetails.ui.sections

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.FileUpload
import androidx.compose.material.icons.rounded.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import animations.NetworkButtonIconAnimation
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import utils.SpacerH
import view.consts.Paddings
import view.theme.colors.CustomColors
import widgets.sections.CreateButtonSection

@Composable
internal fun ColumnScope.ButtonSection(
    isEditable: Boolean,
    isEditing: Boolean,
    requestText: String,
    category: ItemCategory?,
    deliveryTypes: List<DeliveryType>,
    initialText: String,
    initialDeliveryTypes: List<DeliveryType>,
    initialCategory: ItemCategory?,
    isLoading: Boolean,
    createOrEditRequest: () -> Unit,
    onAcceptClick: () -> Unit
) {
    if (isEditable) {
        Row(Modifier.padding(horizontal = Paddings.medium)) {
            if (isEditing) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.errorContainer,
                        contentColor = colorScheme.onErrorContainer
                    )
                ) {
                    Icon(Icons.Rounded.DeleteOutline, null)
                }
                SpacerH(Paddings.small)
            }
            CreateButtonSection(
                enabled =
                    (requestText.isNotBlank() && category != null && deliveryTypes.isNotEmpty()) &&
                            (!isEditing || (
                                    initialDeliveryTypes != deliveryTypes ||
                                            initialCategory != category ||
                                            initialText != requestText
                                    )),
                isLoading = isLoading,
                text = if (isEditing) "Редактировать" else "Создать заявку",
                icon = if (isEditing) Icons.Rounded.Edit else Icons.Rounded.FileUpload,
                showAnimation = !isEditing,
                modifier = Modifier.fillMaxWidth()
            ) {
                createOrEditRequest()
            }
        }
    } else {
        val isDark = isSystemInDarkTheme()
        Button(
            onClick = onAcceptClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isDark) CustomColors.greenContainer else CustomColors.green,
                contentColor = if (isDark) CustomColors.green else CustomColors.greenContainer
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            NetworkButtonIconAnimation(Icons.Rounded.Verified, isLoading)
            SpacerH(Paddings.small)
            Text("Откликнуться", maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}