@file:OptIn(ExperimentalSharedTransitionApi::class)

package common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import view.consts.Paddings

@Suppress("FunctionName")
internal fun LazyGridScope.TransitionColumnHeader(
    currentContentType: ContentType?,
    contentType: ContentType,
) {
    item(
        key = contentType.key,
        span = { GridItemSpan(maxCurrentLineSpan) },
        contentType = contentType
    ) {
        TransitionHeader(
            isVisible = currentContentType != contentType,
            contentType = contentType,
            modifier = Modifier.padding(start = Paddings.medium)
        )
    }
}

@Composable
internal fun TransitionHeader(
    contentType: ContentType,
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    Column { // lol, but animation is better...
        AnimatedContent(
            if (isVisible) when (contentType) {
                ContentType.Catalog -> "Каталог"
                ContentType.MyRequests -> "Мои заявки"
            } else "",
            transitionSpec = { (fadeIn()).togetherWith(fadeOut()) }
        ) { text ->
            Text(
                text = text,
                modifier =
                    modifier,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontWeight = FontWeight.Medium,
                style = typography.headlineLargeEmphasized,
                autoSize = TextAutoSize.StepBased(maxFontSize = typography.headlineLargeEmphasized.fontSize)
            )
        }
    }
}