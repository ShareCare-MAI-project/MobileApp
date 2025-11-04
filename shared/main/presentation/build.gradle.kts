
plugins {
    id("presentation-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.compose.ui:ui-backhandler:1.9.1")
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Main.presentation)
}