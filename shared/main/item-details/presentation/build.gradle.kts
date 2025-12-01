plugins {
    id("presentation-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.backhandler)
            implementation(project(Modules.Main.Common.presentation))
            implementation(project(Modules.Main.ItemDetails.domain))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Main.ItemDetails.presentation)
}