package common

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import dev.chrisbanes.haze.HazeState

val LocalTransitionHazeState: ProvidableCompositionLocal<HazeState> =
    staticCompositionLocalOf {
        error("No TransitionHazeState provided")
    }