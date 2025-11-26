package widgets.expressiveList

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


data class ExpressiveListItem(
    val icon: ImageVector?,
    val text: String,
    val containerColor: Color? = null,
    val contentColor: Color? = null,
    val onClick: () -> Unit// only for `generated`
)