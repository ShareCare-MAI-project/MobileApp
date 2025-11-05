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


// TODO: refactor this
val sharedPath = ":shared"
listOf(
    "core", "di", "root", "utils", "utils-compose"
).forEach { module ->
    include("$sharedPath:$module")
}

val mainPath = "$sharedPath:main"
include("$mainPath:flow:presentation")
include("$mainPath:common:presentation")
listOf(
    "share-care", "find-help", "item-details"
).forEach { module ->
    val subModules = listOf("data", "domain", "presentation")
    subModules.forEach { subModule ->
        include("$mainPath:$module:$subModule")
    }
}

listOf(
    "hello", "auth", "settings", "item-editor"
).forEach { module ->
    val subModules = listOf("data", "domain", "presentation")
    subModules.forEach { subModule ->
        include("$sharedPath:$module:$subModule")
    }
}