package findHelp.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import common.ContentType
import common.MainLazyColumn
import common.TransitionColumnHeader
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import findHelp.components.FindHelpComponent
import utils.SpacerV
import view.consts.Paddings

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun FindHelpUI(
    topPadding: Dp,
    bottomPadding: Dp,
    lazyListState: LazyListState,
    currentContentType: ContentType?,
    component: FindHelpComponent,
) {

    val hazeState = rememberHazeState()
    MainLazyColumn(
        topPadding = topPadding,
        emptyBottomPadding = bottomPadding,
        lazyListState = lazyListState
    ) {
        TransitionColumnHeader(
            contentType = ContentType.Catalog,
            currentContentType = currentContentType
        )
        items(50, key = { it }, contentType = { ContentType.Catalog }) {
            Row(
                Modifier.fillMaxWidth().hazeSource(hazeState),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                (1..3).forEach { _ ->
                    Text("FindHelp")
                }
            }
        }

        item(contentType = ContentType.Catalog) { SpacerV(Paddings.medium) }

        TransitionColumnHeader(
            contentType = ContentType.MyRequests,
            currentContentType = currentContentType
        )
        items(50, key = { (it+1) * 100  }, contentType = { ContentType.MyRequests }) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                (1..3).forEach { _ ->
                    Text("FindHelp")
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun FindHelpUIPreview(
//    topSafePadding: Dp,
//    bottomShadowHeight: Dp,
//    emptyBottomPadding: Dp,
//    hazeState: HazeState,
//) {
//    FindHelpUI(
//        topSafePadding = topSafePadding,
//        bottomShadowHeight = bottomShadowHeight,
//        emptyBottomPadding = emptyBottomPadding,
//        hazeState = hazeState,
//        component = FakeFindHelpComponent
//    )
//}