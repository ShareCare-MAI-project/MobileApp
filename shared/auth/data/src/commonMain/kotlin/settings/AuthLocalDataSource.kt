package settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

class AuthLocalDataSource(
    private val settings: Settings
) {

    fun saveToken(token: String) {
        settings[TOKEN_KEY] = token
    }

    fun saveName(name: String) {
        settings[NAME_KEY] = name
    }

    fun fetchToken(): String? {
        val token = settings[TOKEN_KEY, ""]
        return token.ifEmpty { null }
    }

    fun fetchName(): String? {
        return settings[NAME_KEY, ""].ifEmpty { null }
    }

    companion object {
        const val TOKEN_KEY = "tokenKey"
        const val NAME_KEY = "nameKey"
    }
}