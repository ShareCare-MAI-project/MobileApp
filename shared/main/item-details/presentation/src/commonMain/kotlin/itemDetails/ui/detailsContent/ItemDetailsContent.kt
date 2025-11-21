package itemDetails.ui.detailsContent

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import common.detailsTransition.LocalDetailsAnimator
import common.detailsTransition.LocalTransitionHazeState
import common.detailsTransition.SharedAnimation
import common.itemCard.ItemImage
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import itemDetails.components.ItemDetailsComponent
import itemDetails.ui.bottomSheet.isExpanded
import resources.RImages
import utils.fastBackground
import view.consts.Paddings

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun SharedTransitionScope.ItemDetailsContent(
    component: ItemDetailsComponent,
    sheet: @Composable BoxScope.() -> Unit
) {
    val hazeState = LocalTransitionHazeState.current
    val detailsAnimator = LocalDetailsAnimator.current

    val safeContentPaddings = WindowInsets.safeContent.asPaddingValues()
    val topPadding = safeContentPaddings.calculateTopPadding()

    // idk, why but animateColorAsState is blinking 0_o
    val animatedTopBackgroundAlpha by animateFloatAsState(
        if (detailsAnimator.isStableDetailed) 1f
        else 0f,
        animationSpec = tween(300)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            Modifier.pointerInput(Unit) {} // Prohibit Background scrolling
                .padding(top = topPadding)
                .fillMaxWidth()
        ) {
            detailsAnimator.SharedAnimation(
                Modifier
            ) { sheetValue ->
                if (sheetValue.isExpanded()) {
                    ItemImage(
                        path = RImages.LOGO,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(horizontal = Paddings.horizontalListPadding)
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
                    .padding(horizontal = Paddings.horizontalListPadding) // соответствуем image
                    .padding(Paddings.semiSmall),
                hazeState = hazeState
            ) {
                detailsAnimator.onBackSuccessful()
            }

        }

        sheet()


        // Lol, заливаем топ, чтобы не было видно бэкграунд DetailsImagePager
        Box(
            Modifier.fastBackground(colorScheme.background.copy(alpha = animatedTopBackgroundAlpha))
                .fillMaxWidth().height(topPadding)

        )
    }
}