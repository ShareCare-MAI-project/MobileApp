package widgets.surfaceTextField

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import view.consts.Paddings

@Composable
fun SurfaceTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    paddings: PaddingValues = PaddingValues.Zero,
    textFieldModifier: Modifier = Modifier.minimumInteractiveComponentSize().fillMaxWidth(),
    shape: Shape = shapes.large,
    singleLine: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    placeholderText: String? = null,
    imeAction: ImeAction = ImeAction.Unspecified
) {

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    var prevValue: Rect? = remember { null }

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()


    val isPlaceholderInField = state.text.isEmpty() && !isFocused

    Column(Modifier.padding(paddings)) {
        placeholderText?.let { text ->

            AnimatedPlaceholder(
                isVisible = !isPlaceholderInField,
                text = text,
                textStyle = textStyle,
                alpha = 1f,
                modifier = Modifier.padding(start = Paddings.small)
            )
        }


        Surface(
            modifier = modifier,
            shape = shape,
            color = colorScheme.surfaceContainerHigh,
        ) {
            BasicTextField(
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = imeAction
                ),

                modifier = textFieldModifier.bringIntoViewRequester(bringIntoViewRequester),
                state = state,
                cursorBrush = SolidColor(colorScheme.primary),
                lineLimits = if (singleLine) TextFieldLineLimits.SingleLine else TextFieldLineLimits.Default,
                textStyle = textStyle.copy(color = colorScheme.onSurface), // wtf? idk
                decorator = { innerTextField ->
                    Box(
                        Modifier.width(IntrinsicSize.Max)
                            .padding(
                                horizontal = Paddings.semiMedium,
                                vertical = Paddings.small
                            ),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        placeholderText?.let { text ->
                            AnimatedPlaceholder(
                                isVisible = isPlaceholderInField,
                                text = text,
                                textStyle = textStyle,
                                alpha = .8f
                            )
                        }
                        innerTextField()
                    }
                },
                onTextLayout = {
                    val flow = it.asFlow()
                    coroutineScope.launch {
                        flow.collectLatest { textLayoutResult ->
                            textLayoutResult?.let {
                                var cursorRect =
                                    textLayoutResult.getCursorRect(state.selection.start)
                                cursorRect = cursorRect.copy(bottom = cursorRect.bottom + 140f)
                                if (prevValue != cursorRect) {
                                    prevValue = cursorRect
                                    println("wtf to: ${cursorRect}")
                                    bringIntoViewRequester.bringIntoView(
                                        cursorRect
                                    )
                                }

                            }

                        }
                    }

                },
                interactionSource = interactionSource,
            )
        }
    }
}

@Composable
private fun AnimatedPlaceholder(
    isVisible: Boolean,
    text: String,
    textStyle: TextStyle,
    alpha: Float,
    modifier: Modifier = Modifier,
) {
    Column {
        AnimatedVisibility(isVisible) {
            Text(
                text = text,
                style = textStyle.copy(fontWeight = FontWeight.Medium),
                color = colorScheme.onSurfaceVariant.copy(alpha = alpha),
                maxLines = 1,
                modifier = modifier
            )
        }
    }
}