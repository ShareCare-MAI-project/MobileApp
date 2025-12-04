package itemDetails.ui.bottomSheet

import alertsManager.AlertState
import alertsManager.AlertsManager
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.AddBox
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import itemDetails.components.ItemDetailsComponent
import view.theme.colors.CustomColors
import widgets.expressiveList.ExpressiveListItem

@Composable
fun rememberButtons(
    isOwner: Boolean,
    recipientId: String?,
    component: ItemDetailsComponent,
    closeSheet: (() -> Unit) -> Unit
): List<ExpressiveListItem> {
    val containerColor = colorScheme.surfaceContainerHighest.copy(alpha = .6f)
    val primaryColor = colorScheme.primary
    val errorColor = colorScheme.error
    val greenColor = if (isSystemInDarkTheme()) CustomColors.green else CustomColors.darkGreen
    return remember(recipientId) {
        buildList<ExpressiveListItem> {
            if (isOwner) {
                if (recipientId == null) {
                    add(
                        ExpressiveListItem(
                            icon = Icons.Rounded.DeleteOutline,
                            text = "Удалить",
                            containerColor = containerColor,
                            contentColor = errorColor
                        ) {
                            component.deleteItem(closeSheet)
                        }
                    )
                    add(
                        ExpressiveListItem(
                            icon = Icons.Rounded.Edit,
                            text = "Редактировать",
                            containerColor = containerColor,
                            contentColor = primaryColor
                        ) {
                            closeSheet {}
                            component.onEditClick()
                        }
                    )
                }
            }
            if (recipientId != null) {
                add(
                    ExpressiveListItem(
                        icon = Icons.Rounded.Close,
                        text = if (isOwner) "Отклонить" else "Отменить",
                        containerColor = containerColor,
                        contentColor = errorColor
                    ) {
                        component.denyItem()
                    }
                )
                add(
                    ExpressiveListItem(
                        icon = Icons.Rounded.Done,
                        text = if (isOwner) "Отдал" else "Получил",
                        containerColor = containerColor,
                        contentColor = greenColor
                    ) { component.acceptItem(closeSheet) }
                )
                add(
                    ExpressiveListItem(
                        icon = Icons.AutoMirrored.Rounded.Send,
                        text = "Переписка",
                        containerColor = containerColor,
                        contentColor = primaryColor
                    ) {
                        AlertsManager.push(
                            AlertState.SnackBar(
                                component.telegram.value ?: "Неизвестный телеграм"
                            )
                        )
                    }
                )
            } else if (!isOwner) {
                add(
                    ExpressiveListItem(
                        icon = Icons.Rounded.AddBox,
                        text = "Забрать вещь",
                        containerColor = containerColor,
                        contentColor = greenColor,
                        blendy = .15f
                    ) { component.takeItem() }
                )
            }
        }
    }
}