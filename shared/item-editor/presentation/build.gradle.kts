plugins {
    id("presentation-setup")
}

kotlin {
    sourceSets {
    }
}

android {
    namespace = Config.Android.namespace(Modules.ItemEditor.presentation)
}