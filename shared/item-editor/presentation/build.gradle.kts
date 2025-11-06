plugins {
    id("presentation-setup")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.accompanist.permissions)

            implementation(libs.androidx.camera.compose)
            implementation(libs.androidx.camera.lifecycle)
            implementation(libs.androidx.camera.camera2)
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.ItemEditor.presentation)
}