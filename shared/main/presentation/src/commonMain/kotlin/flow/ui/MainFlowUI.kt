package flow.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import common.ContentType
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import findHelp.ui.FindHelpUI
import flow.components.MainFlowComponent
import flow.components.MainFlowComponent.Child
import flow.ui.bottomBar.MainBottomBar
import flow.ui.topBar.MainTopBar
import foundation.scrollables.BottomScrollEdgeFade
import foundation.scrollables.ScrollEdgeFade
import foundation.scrollables.ScrollEdgeShadowHeight
import view.consts.Paddings

@OptIn(
    ExperimentalDecomposeApi::class, ExperimentalSharedTransitionApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun MainFlowUI(
    component: MainFlowComponent
) {
    var currentContentType: ContentType? by remember { mutableStateOf(null) }

    val stack by component.stack.subscribeAsState()
    val currentChild = stack.active.instance

    val density = LocalDensity.current

    val hazeState = rememberHazeState()

    val safeContentInsets = WindowInsets.safeContent
    val imeInsets = WindowInsets.ime

    val topSafePadding = with(density) { safeContentInsets.getTop(density).toDp() }
    val bottomSafePadding = with(density) { safeContentInsets.getBottom(density).toDp() }

    val bottomShadowHeight = bottomSafePadding

    val imePadding = with(density) { imeInsets.getBottom(density).toDp() }


    // храню их здесь, а не в в ui-компонентах, т.к. fade идёт с MainFlow (чтоб не мерцало)
    val lazyGridStateFindHelp = rememberLazyGridState()
    val lazyGridStateShareCare = rememberLazyGridState()

    val currentLazyGridState =
        if (currentChild is Child.FindHelpChild) lazyGridStateFindHelp else lazyGridStateShareCare

    Scaffold(
        bottomBar = {
            val bottomBarPadding by animateDpAsState(with(bottomSafePadding - imePadding) {
                if (this > Paddings.semiLarge) this + Paddings.semiSmall
                else Paddings.semiLarge // if there is no safeContentBottomPadding
            }, animationSpec = spring(stiffness = Spring.StiffnessVeryLow))

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
                navigateTo = { cfg -> component.navigateTo(cfg) }
            )
        },
        topBar = {
            MainTopBar(
                modifier = Modifier.fillMaxWidth().padding(top = topSafePadding),
                hazeState = hazeState,
                currentContentType = currentContentType
            )
        }
    ) { paddings ->
        val scaffoldTopPadding = paddings.calculateTopPadding()

        val topBarHeight = scaffoldTopPadding - topSafePadding

        val topBarBottomPx = with(density) {
            (scaffoldTopPadding).roundToPx()
        }

        val topPadding = scaffoldTopPadding + Paddings.medium

        val topPaddingPx = with(density) {
            (topPadding).roundToPx()
        }

        // Получаем первый элемент контента, который находится ниже topBar
        currentContentType = currentLazyGridState.layoutInfo.visibleItemsInfo
            .asSequence()
            .firstOrNull { item ->
                (item.offset.y + item.size.height) > topBarBottomPx
            }?.contentType as? ContentType


        // просто добавляет отступ в скролл, без тени
        val bottomPadding =
            bottomSafePadding + paddings.calculateBottomPadding() + Paddings.endListPadding



        Box(modifier = Modifier.fillMaxSize().hazeSource(hazeState, key = "MainFlow")) {
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
                        topPadding = topPadding,
                        bottomPadding = bottomPadding,
                        component = child.findHelpComponent,
                        lazyGridState = lazyGridStateFindHelp,
                        currentContentType = currentContentType
                    )

                    is Child.ShareCareChild -> TODO()

                }
            }

            // overlay fadings
            ScrollEdgeFade(
                modifier = Modifier.fillMaxWidth()
                    .align(Alignment.TopStart),
                solidHeight = topSafePadding / 2,
                shadowHeight = ScrollEdgeShadowHeight.big + topBarHeight,
                isVisible = currentLazyGridState.canScrollBackward
            )

            BottomScrollEdgeFade(
                modifier = Modifier.fillMaxWidth().align(Alignment.BottomStart),
                isVisible = currentLazyGridState.canScrollForward,
                solidHeight = bottomSafePadding / 2,
                shadowHeight = ScrollEdgeShadowHeight.big,
            )

            Text(stack.items.size.toString(), modifier = Modifier.padding(Paddings.big))
        }
    }

}