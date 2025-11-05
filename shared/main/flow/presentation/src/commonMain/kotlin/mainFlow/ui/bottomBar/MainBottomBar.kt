package mainFlow.ui.bottomBar

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Interests
import androidx.compose.material.icons.rounded.VolunteerActivism
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import careshare.shared.main.flow.presentation.generated.resources.Res
import careshare.shared.main.flow.presentation.generated.resources.bar_find_help
import careshare.shared.main.flow.presentation.generated.resources.bar_share_care
import dev.chrisbanes.haze.HazeState
import foundation.layouts.ThreeComponentsLayout
import kotlinx.coroutines.launch
import mainFlow.components.MainFlowComponent.Child
import mainFlow.components.MainFlowComponent.Config
import utils.value
import view.consts.Paddings

@Composable
internal fun MainBottomBar(
    modifier: Modifier,
    child: Child,
    hazeState: HazeState,
    navigateTo: (Config) -> Unit,
    onFABButtonClick: (Boolean) -> Unit,
    lazyGridStateFindHelp: LazyGridState,
    lazyGridStateShareCare: LazyGridState
) {
    val coroutineScope = rememberCoroutineScope()
    val isFindHelp = child is Child.FindHelpChild

    ThreeComponentsLayout(
        modifier = modifier,
        isCenter = true,
        isSpaceAbove = true,
        paddingBetween = Paddings.semiSmall,
        leftContent = {
            BottomBarButton(
                isSelected = isFindHelp,
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
                isFindHelp = isFindHelp
            ) {
                onFABButtonClick(isFindHelp)
            }
        },
        rightContent = {
            BottomBarButton(
                isSelected = !isFindHelp,
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

