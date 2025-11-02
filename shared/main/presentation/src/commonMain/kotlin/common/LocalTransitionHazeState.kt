package common

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalWithComputedDefaultOf
import dev.chrisbanes.haze.HazeState

val LocalTransitionHazeState: ProvidableCompositionLocal<HazeState> =
    compositionLocalWithComputedDefaultOf {
        HazeState()
    }