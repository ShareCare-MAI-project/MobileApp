package mainFlow.ui.topBar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import dev.chrisbanes.haze.HazeState
import utils.SpacerH
import view.consts.Paddings
import widgets.glass.GlassCard

@Composable
internal fun SearchBar(modifier: Modifier = Modifier, hazeState: HazeState, isTitle: Boolean) {
    val textStyle = typography.bodyLarge
    GlassCard(
        modifier = modifier,
        hazeState = hazeState,
        isReversedProgressive = true
    ) {
        AnimatedContent(
            isTitle,
            modifier = Modifier.animateContentSize()
        ) { isIconMode ->

            Box(
                Modifier.fillMaxHeight().then(
                    if (isIconMode) {
                        Modifier.aspectRatio(1f)
                    } else Modifier.fillMaxWidth()
                )
            ) {
                if (!isIconMode) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.Search, contentDescription = null)
                        SpacerH(Paddings.small)
                        BasicTextField(
                            value = "",
                            onValueChange = {

                            },
                            modifier = Modifier.onFocusChanged { focusState ->
//                                isSearchTextFieldFocused = focusState.isFocused
                                println("STATE: $focusState")
                            },
                            textStyle = textStyle,
                            decorationBox = { innerTextField ->
                                Box(
                                    Modifier.width(IntrinsicSize.Max),
                                    contentAlignment = Alignment.CenterStart
                                ) {
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
                } else {
                    Box(
                        Modifier.matchParentSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Rounded.Search,
                            modifier = Modifier.matchParentSize().scale(1.2f),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}