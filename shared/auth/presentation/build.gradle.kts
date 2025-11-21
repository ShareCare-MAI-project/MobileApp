plugins {
    id("presentation-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.backhandler)
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Auth.presentation)
}