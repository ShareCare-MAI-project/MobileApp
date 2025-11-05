plugins {
    id("presentation-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(Modules.Main.Common.presentation))
            implementation(project(Modules.Main.ShareCare.presentation))
            implementation(project(Modules.Main.FindHelp.presentation))
            implementation(project(Modules.Main.ItemDetails.presentation))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Main.Flow.presentation)
}