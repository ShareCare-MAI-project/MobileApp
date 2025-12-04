plugins {
    id("presentation-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(Modules.Auth.domain))
            implementation(project(Modules.Settings.domain))
            implementation(project(Modules.Settings.presentation))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Profile.presentation)
}