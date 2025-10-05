@file:Suppress("ConstPropertyName")
object Modules {
    const val composeApp = "composeApp"

    const val sharedPath = ":shared"

    const val root = "$sharedPath:root"
    const val core = "$sharedPath:core"
    const val di = "$sharedPath:di"
    const val utils = "$sharedPath:utils"
    const val utilsCompose = "$sharedPath:utils-compose"

    object Hello {
        private const val modulePath = "$sharedPath:hello"

        const val data = "$modulePath:data"
        const val domain = "$modulePath:domain"
        const val presentation = "$modulePath:presentation"
    }

    object Auth {
        private const val modulePath = "$sharedPath:auth"

        const val data = "$modulePath:data"
        const val domain = "$modulePath:domain"
        const val presentation = "$modulePath:presentation"
    }
    object Main {
        private const val modulePath = "$sharedPath:main"

        const val data = "$modulePath:data"
        const val domain = "$modulePath:domain"
        const val presentation = "$modulePath:presentation"
    }
    object Settings {
        private const val modulePath = "$sharedPath:settings"

        const val data = "$modulePath:data"
        const val domain = "$modulePath:domain"
        const val presentation = "$modulePath:presentation"
    }
}