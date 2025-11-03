package platform

enum class Platform {
    iOS, Android
}

expect val currentPlatform: Platform