package foundation.scrollables

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import utils.SpacerV

@Composable
fun VerticalScrollableBox(
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
    scrollModifier: Modifier = Modifier.verticalScroll(scrollState),
    windowInsets: WindowInsets = WindowInsets(),
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    isBottomPadding: Boolean = true,
    content: @Composable BoxScope.() -> Unit,
) {
    val density = LocalDensity.current

    val topInsetsHeightDp = with(density) { windowInsets.getTop(this).toDp() }
    val bottomInsetsHeightDp = with(density) { windowInsets.getBottom(this).toDp() }

    val isScrollable = (scrollState.canScrollForward || scrollState.canScrollBackward)

    Box(
        modifier = modifier,
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints,
    ) {
        Column(
            Modifier
                .then(scrollModifier)

        ) {
            SpacerV(topInsetsHeightDp)
            Box() {
                content()
            }


            if (isBottomPadding && isScrollable) {
                SpacerV(bottomInsetsHeightDp)
            }
        }

        if (isScrollable) {
            ScrollEdgeFade(
                modifier = Modifier.fillMaxWidth().align(Alignment.TopStart),
                solidHeight = topInsetsHeightDp / 2,
                isVisible = scrollState.canScrollBackward,
            )

            BottomScrollEdgeFade(
                modifier = Modifier.fillMaxWidth().align(Alignment.BottomStart),
                solidHeight = bottomInsetsHeightDp,
                isVisible = scrollState.canScrollForward
            )
        }

    }
}

