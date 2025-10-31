package itemDetails.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.SeekableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.cardTitle
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import foundation.AsyncImage
import itemDetails.components.ItemDetailsComponent
import kotlinx.coroutines.launch
import resources.RImages
import view.consts.Paddings
import view.consts.Sizes

@OptIn(
    ExperimentalHazeMaterialsApi::class,
    ExperimentalSharedTransitionApi::class
)//, ExperimentalSharedTransitionApi::class)
@Composable
fun ItemDetailsUI(
    component: ItemDetailsComponent,
    hazeState: HazeState,
    topPadding: Dp
) {
//    Modifier.sharedElement()

    val density = LocalDensity.current

    val isDarkTheme = isSystemInDarkTheme()

    val screenHeight = LocalWindowInfo.current.containerDpSize.height - topPadding

    Box(
        modifier = Modifier
            .pointerInput(Unit) {} // Prohibit Background scrolling
            .fillMaxSize()
            .padding(top = topPadding)
    ) {
        AsyncImage(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = Paddings.medium)
                .fillMaxWidth()
                .height(350.dp)
                .clip(RoundedCornerShape(20.dp - Paddings.small))
                .hazeSource(hazeState),
            contentDescription = null,
            path = RImages.LOGO
        )

        var a by remember { mutableStateOf(false) }
        val startIntensity by animateFloatAsState(if (a) .4f else .0f)
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(screenHeight - 300.dp)
                .clip(shapes.extraExtraLarge)
                .fillMaxWidth()
                .hazeEffect(
                    state = hazeState,
                    style = HazeMaterials.thick(colorScheme.background)
                ) {
                    progressive = HazeProgressive.verticalGradient(
                        endY = with(density) {
                            50.dp.toPx()
                        },
                        startIntensity = startIntensity,
                        endIntensity = if (isDarkTheme) .7f else .6f
                    )
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier.padding(top = Paddings.semiMedium).size(Sizes.dragHandleSize)
                    .clip(shapes.medium).background(
                        colorScheme.onBackground.copy(
                            alpha = (startIntensity + .6f).coerceAtMost(1f)
                        )
                    )
            )

            val seekableTransitionState = remember {
                SeekableTransitionState<Boolean>(false)
            }


            val transition = rememberTransition(transitionState = seekableTransitionState)

            val coroutineScope = rememberCoroutineScope()

            Slider(modifier = Modifier.height(48.dp),
                value = seekableTransitionState.fraction,
                onValueChange = {
                    coroutineScope.launch {
                        if (!seekableTransitionState.currentState) {
                            seekableTransitionState.seekTo(it, true)
                        } else {
                            // seek to the previously navigated index
                            seekableTransitionState.seekTo(it, false)
                        }
                    }
                })

            SharedTransitionLayout(Modifier) {
                transition.AnimatedContent { x ->
                    Column {
                        Box(Modifier.fillMaxWidth().height(50.dp)) {
                            if (x) {
                                Text(
                                    "meow", modifier = Modifier
                                        .sharedElement(
                                            rememberSharedContentState("key"),
                                            this@AnimatedContent
                                        )
                                )
                            }
                        }

                        if (!x) {
                            Text(
                                "meow", modifier = Modifier
                                    .sharedElement(
                                        rememberSharedContentState("key"),
                                        this@AnimatedContent
                                    )
                            )
                        }
                    }
                }
            }

            Text(
                text = cardTitle,
                textAlign = TextAlign.Center,
                style = typography.headlineLargeEmphasized,
                modifier = Modifier
                    .padding(horizontal = Paddings.medium),
                fontWeight = FontWeight(450)
            )

            Column(Modifier.verticalScroll(rememberScrollState())) {
                for (i in 1..50) {
                    Text("asdasdas")
                }
                Button({ a = !a }) {
                    Text("$a")
                }
            }
        }
    }
}