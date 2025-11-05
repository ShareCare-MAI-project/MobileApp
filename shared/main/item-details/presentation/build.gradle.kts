plugins {
    id("presentation-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.compose.ui:ui-backhandler:1.9.1")
            implementation(project(Modules.Main.Common.presentation))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Main.ItemDetails.presentation)
}