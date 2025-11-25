plugins {
    id("presentation-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(Modules.Auth.domain))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Profile.presentation)
}