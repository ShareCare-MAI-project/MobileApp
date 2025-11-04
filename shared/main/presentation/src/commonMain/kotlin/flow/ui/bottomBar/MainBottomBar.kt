package flow.ui.bottomBar

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Interests
import androidx.compose.material.icons.rounded.VolunteerActivism
import androidx.compose.material3.MaterialTheme.colorScheme
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
    lazyGridStateFindHelp: LazyGridState,
    lazyGridStateShareCare: LazyGridState
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
                selectedColor = colorScheme.secondaryContainer,
                text = Res.string.bar_find_help.value,
                icon = Icons.Rounded.Interests,
                hazeState = hazeState,
                onLongClick = {
                    coroutineScope.launch {
                        lazyGridStateFindHelp.animateScrollToItem(0)
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
                selectedColor = colorScheme.tertiaryContainer,
                text = Res.string.bar_share_care.value,
                icon = Icons.Rounded.VolunteerActivism,
                hazeState = hazeState,
                onLongClick = {
                    coroutineScope.launch {
                        lazyGridStateShareCare.animateScrollToItem(0)
                    }
                }
            ) {
                navigateTo(Config.ShareCare)
            }
        }
    )
}

