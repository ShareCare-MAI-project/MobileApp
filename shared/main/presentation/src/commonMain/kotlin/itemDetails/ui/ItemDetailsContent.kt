package itemDetails.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import common.ItemImage
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import flow.ui.DetailedItemAnimationManager
import itemDetails.components.ItemDetailsComponent
import itemDetails.ui.bottomSheet.isExpanded
import resources.RImages
import utils.fastBackground
import view.consts.Paddings
import widgets.glass.GlassCard
import widgets.glass.GlassCardFunctions

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun SharedTransitionScope.ItemDetailsContent(
    component: ItemDetailsComponent,
    topPadding: Dp,
    hazeState: HazeState,
    detailedItemAnimationManager: DetailedItemAnimationManager,
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
            detailedItemAnimationManager.transition.AnimatedContent(
                transitionSpec = { fadeIn(tween(0)).togetherWith(fadeOut(tween(0))) }
            ) { sheetValue ->
                if (sheetValue.isExpanded()) {
                    ItemImage(
                        path = RImages.LOGO,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(horizontal = Paddings.semiMedium)
                            .height(350.dp)
                            .hazeSource(hazeState, zIndex = 0f),
                        id = component.itemId,
                        detailedItemId = component.itemId,
                        animatedContentScope = this@AnimatedContent
                    )
                }
            }
            val state = rememberPagerState() { 3 }
            Crossfade(
                detailedItemAnimationManager.isStableDetailed,
                modifier = Modifier.renderInSharedTransitionScopeOverlay()
                    .hazeSource(hazeState, zIndex = 1f),
                animationSpec = tween(if (state.currentPage == 0) 0 else 300)
            ) { isPager ->
                if (isPager) {
                    HorizontalPager(
                        state = state,
                        modifier = Modifier//.align(Alignment.TopCenter)
                            .height(350.dp)
                            .fillMaxWidth()
                            .fastBackground(if (detailedItemAnimationManager.isStableDetailed) colorScheme.background else Color.Transparent)
                    ) { index ->
                        ItemImage(
                            path = when(index) {
                                0 -> RImages.LOGO
                                1 -> RImages.LOGO2
                                2 -> RImages.LOGO3
                                else -> RImages.LOGO
                            },
                            modifier = Modifier
                                .padding(horizontal = Paddings.semiMedium)
                                .fillMaxSize(),
                            id = component.itemId,
                            detailedItemId = null,
                            animatedContentScope = null
                        )

                    }
                }
            }
            val l = rememberCoroutineScope()
            BackButton(
                isVisible =
                    detailedItemAnimationManager.isStableDetailed,
                modifier = Modifier
                    .renderInSharedTransitionScopeOverlay()
                    .padding(horizontal = Paddings.semiMedium)
                    .padding(Paddings.semiSmall),
                hazeState = hazeState
            ) {
                detailedItemAnimationManager.onBackSuccessful()
            }

        }

        sheet()
    }
}

@Composable
private fun BackButton(
    isVisible: Boolean,
    modifier: Modifier,
    hazeState: HazeState,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        modifier = modifier,
        enter = fadeIn() + scaleIn(
            spring(
                stiffness = Spring.StiffnessLow,
                dampingRatio = Spring.DampingRatioMediumBouncy
            )
        ),
        exit = fadeOut() + scaleOut()
    ) {
        GlassCard(
            // Because of Padding...(about clip)
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    onClick()
                },
            hazeState = hazeState,
            shape = CircleShape,
            contentPadding = PaddingValues(Paddings.semiMedium),
            contentAlignment = Alignment.Center,
            tint = Color.Transparent,
            hazeTint = GlassCardFunctions.getHazeTintColor(
                tint = null,
                containerColor = colorScheme.primaryContainer,
                containerColorAlpha = .4f
            ),
            borderColor = GlassCardFunctions.getBorderColor(
                tint = null,
                containerColor = colorScheme.primaryContainer,
                containerColorAlpha = .1f
            ),
            contentColor = Color.White
        ) {
            Icon(Icons.AutoMirrored.Rounded.ArrowBackIos, contentDescription = null)
        }
    }
}