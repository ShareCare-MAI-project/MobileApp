// Couldn't use `libs` here =/
plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
}

kotlin {
    androidTarget()
//    iosArm64()
    iosSimulatorArm64()

    jvmToolchain(Config.Java.intVersion)
}

android {
    compileSdk = Config.Android.compileSdk
    defaultConfig {
        minSdk = Config.Android.minSdk
    }
}