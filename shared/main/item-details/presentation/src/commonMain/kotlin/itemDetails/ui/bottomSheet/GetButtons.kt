package itemDetails.ui.bottomSheet

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
import view.theme.colors.CustomColors
import widgets.expressiveList.ExpressiveListItem

@Composable
fun rememberButtons(
    isOwner: Boolean,
    recipientId: String?
): List<ExpressiveListItem> {
    val containerColor = colorScheme.surfaceContainerHighest.copy(alpha = .6f)
    val primaryColor = colorScheme.primary
    val errorColor = colorScheme.error
    val greenColor = if (isSystemInDarkTheme()) CustomColors.green else CustomColors.darkGreen
    return remember {
        buildList<ExpressiveListItem> {
            if (isOwner) {
                if (recipientId == null) {
                    add(
                        ExpressiveListItem(
                            icon = Icons.Rounded.DeleteOutline,
                            text = "Удалить",
                            containerColor = containerColor,
                            contentColor = errorColor
                        ) {}
                    )
                    add(
                        ExpressiveListItem(
                            icon = Icons.Rounded.Edit,
                            text = "Редактировать",
                            containerColor = containerColor,
                            contentColor = primaryColor
                        ) {}
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
                    ) {}
                )
                add(
                    ExpressiveListItem(
                        icon = Icons.Rounded.Done,
                        text = if (isOwner) "Отдал" else "Получил",
                        containerColor = containerColor,
                        contentColor = greenColor
                    ) {}
                )
                add(
                    ExpressiveListItem(
                        icon = Icons.AutoMirrored.Rounded.Send,
                        text = "Переписка",
                        containerColor = containerColor,
                        contentColor = greenColor
                    ) {}
                )
            } else if (!isOwner) {
                add(
                    ExpressiveListItem(
                        icon = Icons.Rounded.AddBox,
                        text = "Забрать вещь",
                        containerColor = containerColor,
                        contentColor = greenColor,
                        blendy = .15f
                    ) {}
                )
            }
        }
    }
}