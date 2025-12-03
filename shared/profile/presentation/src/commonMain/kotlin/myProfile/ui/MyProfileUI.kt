package myProfile.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import foundation.scrollables.VerticalScrollableBox
import myProfile.components.MyProfileComponent
import myProfile.ui.sections.FontSizeSection
import myProfile.ui.sections.ListSection
import myProfile.ui.sections.NameSection
import myProfile.ui.sections.QuitButtonSection
import myProfile.ui.sections.UsuallyISection
import myProfile.ui.sections.VerificationSection
import utils.SpacerV
import view.consts.Paddings
import widgets.Avatar
import widgets.glass.BackGlassButton

@Composable
fun MyProfileUI(
    component: MyProfileComponent
) {

    val profileData by component.profileData.collectAsState()

    val windowInsets = WindowInsets.safeContent
    val safeContentPaddings = windowInsets.asPaddingValues()
    val topPadding = safeContentPaddings.calculateTopPadding()

    val hazeState = rememberHazeState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Box(
                Modifier.padding(
                    top = topPadding + Paddings.small,
                    start = Paddings.listHorizontalPadding
                )
            ) {
                BackGlassButton(hazeState = hazeState) {

                }
            }
        }
    ) { padding ->
        VerticalScrollableBox(
            modifier = Modifier.fillMaxSize()
                .hazeSource(hazeState),
            windowInsets = windowInsets,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = Paddings.listHorizontalPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpacerV(padding.calculateTopPadding() - topPadding)
                Avatar()

                SpacerV(Paddings.medium)
                NameSection(profileData.name)
                SpacerV(Paddings.medium)

                VerificationSection(
                    isVerified = profileData.isVerified,
                    organizationName = profileData.organizationName
                ) {}
                SpacerV(Paddings.medium)

                UsuallyISection(
                    isHelper = false
                ) { isHelper ->

                }
                SpacerV(Paddings.medium)

                FontSizeSection()
                SpacerV(Paddings.medium)

                ListSection()

                SpacerV(Paddings.medium)

                QuitButtonSection(onClick = component::logout)

                SpacerV(Paddings.endListPadding)
            }
        }

    }
}