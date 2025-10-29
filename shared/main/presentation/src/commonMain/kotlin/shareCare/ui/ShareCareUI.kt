package shareCare.ui

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import shareCare.components.ShareCareComponent

@Composable
fun ShareCareUI(
    topPadding: Dp,
    bottomPadding: Dp,
    lazyListState: LazyListState,
    component: ShareCareComponent
) {
//    MainLazyColumn(
//        topPadding = topPadding,
//        emptyBottomPadding = bottomPadding,
//        lazyListState = lazyListState
//    ) {
//        items(100, key = { it }) {
//            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
//                (1..3).forEach { _ ->
//                    Text("ShareCare")
//                }
//            }
//        }
//    }
}
