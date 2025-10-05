package flow.ui.bottomBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Interests
import androidx.compose.material.icons.rounded.VolunteerActivism
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import careshare.shared.main.presentation.generated.resources.Res
import careshare.shared.main.presentation.generated.resources.bar_find_help
import careshare.shared.main.presentation.generated.resources.bar_share_care
import dev.chrisbanes.haze.HazeState
import flow.components.MainFlowComponent.Child
import flow.components.MainFlowComponent.Config
import kotlinx.coroutines.launch
import utils.value

@Composable
internal fun MainBottomBar(
    modifier: Modifier,
    child: Child,
    hazeState: HazeState,
    navigateTo: (Config) -> Unit,
    lazyListStateFindHelp: LazyListState,
    lazyListStateShareCare: LazyListState
) {
    val coroutineScope = rememberCoroutineScope()
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        BottomBarButton(
            isSelected = child is Child.FindHelpChild,
            text = Res.string.bar_find_help.value,
            icon = Icons.Rounded.Interests,
            hazeState = hazeState,
            onLongClick = {
                coroutineScope.launch {
                    lazyListStateFindHelp.animateScrollToItem(0)
                }
            }
        ) {
            navigateTo(Config.FindHelp)
        }

        MainFAB(
            hazeState = hazeState,
            child = child
        )

        BottomBarButton(
            isSelected = child is Child.ShareCareChild,
            text = Res.string.bar_share_care.value,
            icon = Icons.Rounded.VolunteerActivism,
            hazeState = hazeState,
            onLongClick = {
                coroutineScope.launch {
                    lazyListStateShareCare.animateScrollToItem(0)
                }
            }
        ) {
            navigateTo(Config.ShareCare)
        }
    }
}

