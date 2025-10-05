package flow.ui.topBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import dev.chrisbanes.haze.HazeState
import utils.SpacerH
import view.consts.Paddings
import widgets.glass.GlassCard

@Composable
internal fun MainTopBar(
    modifier: Modifier,
    hazeState: HazeState,
) {

    val textStyle = typography.bodyLarge

    var isSearchTextFieldFocused by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Row(modifier.height(IntrinsicSize.Min)) {
        GlassCard(
            modifier = Modifier.fillMaxHeight().weight(2f, true),
            hazeState = hazeState,
            isReversedProgressive = true
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Rounded.Search, contentDescription = null)
                SpacerH(Paddings.small)
                BasicTextField(
                    value = "",
                    onValueChange = {

                    },
                    modifier = Modifier.onFocusChanged { focusState ->
                        isSearchTextFieldFocused = focusState.isFocused
                        println("STATE: $focusState")
                    },
                    textStyle = textStyle,
                    decorationBox = { innerTextField ->
                        Box(Modifier.width(IntrinsicSize.Max), contentAlignment = Alignment.CenterStart) {
                            if ("".isEmpty()) {
                                Text(
                                    text = "Поиск",
                                    style = textStyle.copy(fontWeight = FontWeight.Medium),
                                    color = colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    modifier = Modifier
                                )
                            }
                            innerTextField()
                        }
                    },
                    cursorBrush = SolidColor(colorScheme.primaryContainer),
                    maxLines = 1,
                    singleLine = true
                )
                Spacer(Modifier.weight(1f, true))
                Icon(
                    Icons.Rounded.Mic,
                    contentDescription = null
                )
            }
        }
        SpacerH(Paddings.medium)
        GlassCard(
            modifier = Modifier.aspectRatio(1f).clickable {},
            hazeState = hazeState,
            shape = CircleShape,
            isReversedProgressive = true,
            tint = colorScheme.tertiaryContainer.copy(alpha = .3f),
            borderColor = colorScheme.tertiaryContainer.copy(alpha = .15f)
        ) {
            if (!isSearchTextFieldFocused) {
                Box(Modifier.matchParentSize(), contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Rounded.Person,
                        modifier = Modifier.matchParentSize().scale(1.2f),
                        contentDescription = null
                    )
                }
            }
        }
    }
}