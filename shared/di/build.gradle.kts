plugins {
    id("shared-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.koin.core)

            implementation(project(Modules.core))

            implementation(project(Modules.Auth.data))




            // Cannot access class 'HttpClient'.
            implementation(libs.ktor.client.core)
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.di)
}