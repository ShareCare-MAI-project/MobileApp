package flow.ui.bottomBar

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Interests
import androidx.compose.material.icons.rounded.VolunteerActivism
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import careshare.shared.main.presentation.generated.resources.Res
import careshare.shared.main.presentation.generated.resources.bar_find_help
import careshare.shared.main.presentation.generated.resources.bar_share_care
import dev.chrisbanes.haze.HazeState
import flow.components.MainFlowComponent.Child
import flow.components.MainFlowComponent.Config
import foundation.layouts.ThreeComponentsLayout
import kotlinx.coroutines.launch
import utils.value
import view.consts.Paddings

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


    ThreeComponentsLayout(
        modifier = modifier,
        isCenter = true,
        isSpaceAbove = true,
        paddingBetween = Paddings.semiSmall,
        leftContent = {
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
        },
        centerContent = {
            MainFAB(
                hazeState = hazeState,
                child = child
            )
        },
        rightContent = {
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
    )
}

