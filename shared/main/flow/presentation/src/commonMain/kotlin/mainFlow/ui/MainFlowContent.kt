package mainFlow.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.LocalBringIntoViewSpec
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.slot.activate
import common.detailsInterfaces.DetailsConfig
import common.grid.ContentType
import common.grid.LocalCurrentContentType
import common.grid.LocalSpacePaddings
import common.grid.SpacePaddings
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import findHelp.ui.FindHelpUI
import foundation.scrollables.BottomScrollEdgeFade
import foundation.scrollables.ScrollEdgeFade
import foundation.scrollables.ScrollEdgeShadowHeight
import mainFlow.components.MainFlowComponent
import mainFlow.components.MainFlowComponent.Child
import mainFlow.components.MainFlowComponent.Output
import mainFlow.ui.bottomBar.MainBottomBar
import mainFlow.ui.topBar.MainTopBar
import ui.ShareCareUI
import view.consts.Paddings

@OptIn(
    ExperimentalDecomposeApi::class,
    ExperimentalSharedTransitionApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun SharedTransitionScope.MainFlowContent(
    component: MainFlowComponent,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current

    val safeContentPaddings = WindowInsets.safeContent.asPaddingValues()
    val topPadding = safeContentPaddings.calculateTopPadding()
    val bottomPadding = safeContentPaddings.calculateBottomPadding()

    val imeInsetsPaddings = WindowInsets.ime.asPaddingValues()
    val imePadding = imeInsetsPaddings.calculateBottomPadding()

    val bottomBarPadding by animateDpAsState(with(bottomPadding - imePadding) {
        if (this > Paddings.semiLarge) this + Paddings.semiSmall
        else Paddings.semiLarge // if there is no safeContentBottomPadding
    }, animationSpec = spring(stiffness = Spring.StiffnessVeryLow))


    val stack by component.stack.subscribeAsState()
    val currentChild = stack.active.instance
    var currentContentType: ContentType? by remember { mutableStateOf(null) }

    // храню их здесь, а не в в ui-компонентах, т.к. fade идёт с MainFlow (чтоб не мерцало)
    val lazyGridStateFindHelp = rememberLazyGridState()
    val lazyGridStateShareCare = rememberLazyGridState()

    val currentLazyGridState =
        if (currentChild is Child.FindHelpChild) lazyGridStateFindHelp else lazyGridStateShareCare

    val hazeState = rememberHazeState()

    Scaffold(
        bottomBar = {
            MainBottomBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom =
                            bottomBarPadding
                    ),
                child = currentChild,
                hazeState = hazeState,
                lazyGridStateFindHelp = lazyGridStateFindHelp,
                lazyGridStateShareCare = lazyGridStateShareCare,
                navigateTo = { cfg -> component.navigateTo(cfg) },
                onFABButtonClick = { isFindHelp ->
                    if (isFindHelp) {
                        component.detailsNav.activate(DetailsConfig.RequestDetailsConfig(
                            id = "Create"
                        ))
                    } else {
                        component.output(Output.NavigateToItemEditor)
                    }
                }
            )

        },
        topBar = {

            MainTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = topPadding),
                hazeState = hazeState,
                currentContentType = currentContentType,
                component = component
            )

        },
        modifier = modifier
    ) { paddings ->
        val scaffoldTopPadding = paddings.calculateTopPadding()
        val scaffoldBottomPadding = paddings.calculateBottomPadding()


        val topBarHeight =
            remember(scaffoldTopPadding, topPadding) { scaffoldTopPadding - topPadding }
        val bottomBarHeight =
            remember(scaffoldBottomPadding, bottomPadding) { scaffoldBottomPadding - bottomPadding }

        val topBarHeightPx = with(density) {
            (scaffoldTopPadding).roundToPx()
        }

        LaunchedEffect(
            currentLazyGridState.scrollIndicatorState?.scrollOffset
        ) {
            // Получаем первый элемент контента, который находится ниже topBar
            currentContentType = currentLazyGridState.layoutInfo.visibleItemsInfo
                .asSequence()
                .firstOrNull { item ->
                    (item.offset.y + item.size.height) > topBarHeightPx
                }?.contentType as? ContentType
        }

        // просто добавляют отступ в скролл, без тени
        val topSpacePadding = remember(scaffoldTopPadding) { scaffoldTopPadding + Paddings.medium }
        val bottomSpacePadding = remember(bottomPadding, scaffoldBottomPadding) {
            bottomPadding + scaffoldBottomPadding + Paddings.endListPadding
        }



        val spacePaddings = remember(topSpacePadding, bottomSpacePadding) {
            SpacePaddings(
                top = topSpacePadding,
                bottom = bottomSpacePadding
            )
        }

        val isTopShadowVisible = currentLazyGridState.canScrollBackward

        val topSolidHeight = topPadding
        val topShadowHeight = ScrollEdgeShadowHeight.big + topBarHeight
        val bottomSolidHeight = bottomPadding / 2
        val bottomShadowHeight = ScrollEdgeShadowHeight.big + bottomBarHeight


        val customBringIntoViewSpec =
            remember(topSolidHeight, topShadowHeight, bottomSolidHeight, bottomShadowHeight, isTopShadowVisible) {
                with(density) {
                    CustomBringIntoViewSpec(
                        topShadowWholePaddingPx = if (isTopShadowVisible) (topSolidHeight + topShadowHeight).toPx() else 0f,
                        bottomShadowWholePaddingPx = (bottomSolidHeight + bottomShadowHeight).toPx()
                    )
                }
            }

        Box(modifier = Modifier.hazeSource(hazeState, key = "MainFlow")) {
            CompositionLocalProvider(
                LocalBringIntoViewSpec provides customBringIntoViewSpec,
                LocalSpacePaddings provides spacePaddings,
                LocalCurrentContentType provides currentContentType
            ) {
                Children(
                    stack = stack,
                    modifier = Modifier.fillMaxSize(),
                    animation = predictiveBackAnimation(
                        backHandler = component.backHandler,
                        fallbackAnimation = stackAnimation(),
                        onBack = component::onBackClicked
                    )
                ) {
                    when (val child = it.instance) {
                        is Child.FindHelpChild -> FindHelpUI(
                            lazyGridState = lazyGridStateFindHelp,
                            component = child.findHelpComponent
                        )

                        is Child.ShareCareChild -> ShareCareUI(
                            lazyGridState = lazyGridStateShareCare,
                            component = child.shareCareComponent
                        )
                    }
                }
            }

            // overlay fadings
            ScrollEdgeFade(
                modifier = Modifier.fillMaxWidth()
                    .align(Alignment.TopStart),
                solidHeight = topSolidHeight,
                shadowHeight = topShadowHeight,
                isVisible = isTopShadowVisible
            )

            BottomScrollEdgeFade(
                modifier = Modifier.fillMaxWidth().align(Alignment.BottomStart),
                solidHeight = bottomSolidHeight,
                shadowHeight = bottomShadowHeight,
                isVisible = currentLazyGridState.canScrollForward
            )

            // TODO: back handler
            Text(stack.items.size.toString(), modifier = Modifier.padding(Paddings.big))
        }
    }
}