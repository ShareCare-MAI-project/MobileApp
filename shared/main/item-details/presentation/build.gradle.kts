plugins {
    id("presentation-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.backhandler)
            implementation(project(Modules.Main.Common.presentation))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Main.ItemDetails.presentation)
}