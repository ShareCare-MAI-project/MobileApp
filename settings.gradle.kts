rootProject.name = "CareShare"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")

val sharedPath = ":shared"
listOf(
    "core", "di", "root", "utils", "utils-compose"
).forEach { module ->
    include("$sharedPath:$module")
}

listOf(
    "hello", "auth", "main", "settings"
).forEach { module ->
    val subModules = listOf("data", "domain", "presentation")
    subModules.forEach { subModule ->
        include("$sharedPath:$module:$subModule")
    }
}