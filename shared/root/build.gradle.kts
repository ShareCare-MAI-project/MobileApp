plugins {
    id("presentation-setup")
    id(libs.plugins.serialization.get().pluginId)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.core)

            implementation(project(Modules.Hello.presentation))
            implementation(project(Modules.Main.presentation))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.core)
}