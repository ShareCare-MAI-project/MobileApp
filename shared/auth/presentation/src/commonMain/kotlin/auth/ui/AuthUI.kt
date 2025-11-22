package auth.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import architecture.launchIO
import auth.components.AuthComponent
import auth.components.AuthProgressState
import foundation.scrollables.VerticalScrollableBox
import utils.SpacerV
import utils.fastBackground
import view.consts.Paddings
import view.consts.Sizes.logoMaxSize

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AuthUI(
    component: AuthComponent
) {

    val authProgressState by component.currentProgressState.collectAsState()

    val windowInsets = WindowInsets.safeContent.exclude(WindowInsets.ime)


    BackHandler(enabled = authProgressState == AuthProgressState.OTPCode) {
        component.onBackClick()
    }

    val snackbarHostState = remember { SnackbarHostState() }


    val verifyCodeResult by component.verifyCodeResult.collectAsState()

    LaunchedEffect(verifyCodeResult) {
        verifyCodeResult.onError { result ->
            this.launchIO {
                snackbarHostState.showSnackbar(message = result.prettyPrint)
            }
        }
    }

    Scaffold(
        Modifier.imePadding().fillMaxSize().fastBackground(colorScheme.background),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) {
        VerticalScrollableBox(
            modifier = Modifier.fillMaxSize(),
            windowInsets = windowInsets,
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.animateContentSize()
            ) {
                SpacerV(Paddings.medium)
                Card(
                    modifier = Modifier
                        .padding(horizontal = Paddings.medium)
                        .sizeIn(maxWidth = logoMaxSize, maxHeight = logoMaxSize)
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    shape = shapes.extraLarge
                ) {}
                SpacerV(Paddings.medium)
                Text(
                    "ДоброДар",
                    textAlign = TextAlign.Center,
                    style = typography.headlineLargeEmphasized,
                    fontWeight = FontWeight.Medium
                )
                SpacerV(Paddings.semiMedium)

                Crossfade(authProgressState) { progressState ->

                    Column(
                        Modifier
                            .sizeIn(maxWidth = logoMaxSize)
                            .padding(horizontal = Paddings.medium),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        when (progressState) {
                            AuthProgressState.PHONE -> {
                                PhoneProgressStateUI(component)
                            }

                            AuthProgressState.OTPCode -> {
                                OTPCodeProgressStateUI(
                                    OTPCode = component.OTPCode,
                                    isLoading = verifyCodeResult.isLoading(),
                                    onNextClick = { component.onVerifyCodeClick() },
                                    onBackClick = { component.onBackClick() }
                                )
                            }
                        }
                    }
                }

            }

        }
    }
}