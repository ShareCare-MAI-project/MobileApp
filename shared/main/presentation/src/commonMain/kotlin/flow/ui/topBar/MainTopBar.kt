package flow.ui.topBar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import common.ContentType
import common.TransitionHeader
import dev.chrisbanes.haze.HazeState
import utils.SpacerH
import view.consts.Paddings
import widgets.glass.GlassCard

@Composable
internal fun MainTopBar(
    modifier: Modifier,
    hazeState: HazeState,
    currentContentType: ContentType?
) {

    val textStyle = typography.bodyLarge
    val density = LocalDensity.current
    var isSearchTextFieldFocused by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    var maxHeight by remember { mutableStateOf(0.dp) }

    val paddingBetweenIcons = animateDpAsState(if (currentContentType == null) Paddings.medium else Paddings.small, animationSpec = tween(400))

    Row(
        modifier.then(
            if (maxHeight > 0.dp) Modifier.height(maxHeight)
            else Modifier.height(IntrinsicSize.Min)
        ).onSizeChanged {
            with(density) {
                if (maxHeight.roundToPx() < it.height) maxHeight = it.height.toDp()
            }
        }) {
        Row(
            modifier = Modifier.weight(2f, fill = true),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AnimatedVisibility(
                currentContentType != null,
                modifier = Modifier,
                enter = fadeIn(tween(600)) + expandHorizontally(),
                exit = fadeOut(tween(600)) + shrinkHorizontally(),
            ) {
                Row {
                    GlassCard(
                        contentPadding = PaddingValues(
                            vertical = Paddings.semiSmall,
                            horizontal = Paddings.semiMedium
                        ),
                        hazeState = hazeState,
                        isReversedProgressive = true,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.CenterStart) {
                            TransitionHeader(
                                contentType = currentContentType ?: ContentType.Catalog,
                                isVisible = true
                            )
                        }

                    }

                    SpacerH(paddingBetweenIcons.value)
                }
            }

            GlassCard(
                modifier = Modifier,
                hazeState = hazeState,
                isReversedProgressive = true
            ) {
                AnimatedContent(
                    currentContentType != null,
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
                                        isSearchTextFieldFocused = focusState.isFocused
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
                            Box(Modifier.matchParentSize(), contentAlignment = Alignment.Center) {
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

        SpacerH(paddingBetweenIcons.value)
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