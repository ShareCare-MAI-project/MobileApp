package common.grid

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

internal val LocalCurrentContentType: ProvidableCompositionLocal<ContentType?> =
    staticCompositionLocalOf {
        error("No LocalCurrentContentType provided")
    }