plugins {
    id("presentation-setup")
    id(libs.plugins.serialization.get().pluginId)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.core)

            implementation(project(Modules.Hello.presentation))
            implementation(project(Modules.Auth.presentation))
            implementation(project(Modules.Auth.domain))
            implementation(project(Modules.Main.Flow.presentation))
            implementation(project(Modules.ItemEditor.presentation))
            implementation(project(Modules.Profile.presentation))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.core)
}