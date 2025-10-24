package flow.ui.topBar

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import common.ContentType
import dev.chrisbanes.haze.HazeState
import foundation.layouts.RightImportantLayout
import foundation.scrollables.ScrollEdgeFadeHorizontal
import utils.SpacerH
import view.consts.Paddings

@Composable
internal fun MainTopBar(
    modifier: Modifier,
    hazeState: HazeState,
    currentContentType: ContentType?
) {


    val density = LocalDensity.current
    var isSearchTextFieldFocused by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    val isTitle = currentContentType != null

    var maxHeight by remember { mutableStateOf(0.dp) }
    var width by remember { mutableStateOf(0.dp) }

    val paddingBetweenIcons = animateDpAsState(
        if (isTitle) Paddings.medium else Paddings.small,
        animationSpec = tween(400)
    )

    RightImportantLayout(
        modifier = modifier.then(
            if (maxHeight > 0.dp) Modifier.height(maxHeight)
            else Modifier.height(IntrinsicSize.Min)
        ).onSizeChanged {
            with(density) {
                if (maxHeight.roundToPx() < it.height) maxHeight = it.height.toDp()
                width = it.width.toDp()
            }
        },
        leftSide = {
            Box() {
                Row(
                    Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TopTitle(
                        modifier = Modifier.widthIn(max = width - 2 * (maxHeight + paddingBetweenIcons.value) - Paddings.medium),
                        isTitle = isTitle,
                        hazeState = hazeState,
                        contentType = currentContentType
                    )
                    if (isTitle) {
                        SpacerH(paddingBetweenIcons.value)
                    } else {
                        SpacerH(Paddings.medium)
                    }
                    SearchBar(
                        modifier = Modifier.then(if (isTitle) Modifier.widthIn(maxHeight) else Modifier),
                        hazeState = hazeState, isTitle = isTitle
                    )
                }
                ScrollEdgeFadeHorizontal(
                    modifier = Modifier,
                    solidWidth = Paddings.semiSmall,
                    isVisible = true,
                    shadowWidth = 40.dp,
                    hazeState = null,
                    isReversed = false
                )
            }

        }, rightSide = {
            SpacerH(paddingBetweenIcons.value)
            AvatarButton(hazeState)
            SpacerH(Paddings.medium)
        }
    )
}