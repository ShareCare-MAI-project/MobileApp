@file:OptIn(ExperimentalSharedTransitionApi::class)

package common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import view.consts.Paddings

@Suppress("FunctionName")
internal fun LazyListScope.TransitionColumnHeader(
    currentContentType: ContentType?,
    contentType: ContentType,
) {
    item(key = contentType.key, contentType = contentType) {
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
    AnimatedContent(
        if (isVisible) when (contentType) {
            ContentType.Catalog -> "Мои вещи"
            ContentType.Catalogx -> "Каталог"
        } else "",
        transitionSpec = { fadeIn().togetherWith(fadeOut()) }
    ) { text ->
        Text(
            text = text,
            modifier =
                modifier,
            style = MaterialTheme.typography.headlineMediumEmphasized

        )
    }
}