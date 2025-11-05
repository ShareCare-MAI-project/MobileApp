plugins {
    id("presentation-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(Modules.Main.Common.presentation))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Main.FindHelp.presentation)
}