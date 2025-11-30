plugins {
    id("data-ktor-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(Modules.Main.RequestDetails.domain))
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.Main.RequestDetails.data)
}
