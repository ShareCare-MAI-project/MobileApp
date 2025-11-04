package itemDetails.ui.detailsContent

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import common.detailsTransition.DetailsAnimator
import common.detailsTransition.SharedAnimation
import common.itemCard.ItemImage
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import itemDetails.components.ItemDetailsComponent
import itemDetails.ui.bottomSheet.isExpanded
import resources.RImages
import view.consts.Paddings

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun SharedTransitionScope.ItemDetailsContent(
    component: ItemDetailsComponent,
    topPadding: Dp,
    hazeState: HazeState,
    detailsAnimator: DetailsAnimator,
    sheet: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            Modifier.pointerInput(Unit) {} // Prohibit Background scrolling
                .padding(top = topPadding)
                .fillMaxWidth()
        ) {

            detailsAnimator.transition.SharedAnimation {
                if (it.isExpanded()) {
                    ItemImage(
                        path = RImages.LOGO,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(horizontal = Paddings.semiMedium)
                            .height(detailsAnimator.imageHeight)
                            .hazeSource(hazeState, zIndex = 0f),
                        id = component.itemId,
                        detailedItemId = component.itemId,
                        animatedContentScope = this@SharedAnimation
                    )
                }
            }

            DetailsImagePager(
                modifier = Modifier.renderInSharedTransitionScopeOverlay()
                    .height(detailsAnimator.imageHeight)
                    .hazeSource(hazeState, zIndex = 1f),
                isStableDetailed = detailsAnimator.isStableDetailed,
                state = detailsAnimator.pagerState
            )

            GlassDetailsBackButton(
                isVisible =
                    detailsAnimator.isStableDetailed,
                modifier = Modifier
                    .renderInSharedTransitionScopeOverlay()
                    .padding(horizontal = Paddings.semiMedium)
                    .padding(Paddings.semiSmall),
                hazeState = hazeState
            ) {
                detailsAnimator.onBackSuccessful()
            }

        }

        sheet()
    }
}