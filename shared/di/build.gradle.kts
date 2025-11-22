plugins {
    id("shared-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.koin.core)

            implementation(project(Modules.core))

            implementation(project(Modules.Auth.data))





            implementation(libs.ktor.client.core) // Cannot access class 'HttpClient'
            implementation(libs.settings.core) // Cannot access class 'Settings'
        }
    }
}

android {
    namespace = Config.Android.namespace(Modules.di)
}