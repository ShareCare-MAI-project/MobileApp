plugins {
    id("shared-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.koin.core)
            implementation(project(Modules.core))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.di)
}