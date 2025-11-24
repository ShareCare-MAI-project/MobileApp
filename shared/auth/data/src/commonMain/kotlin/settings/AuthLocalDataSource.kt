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

    fun fetchToken(): String? {
        val token = settings[TOKEN_KEY, ""]
        return token.ifEmpty { null }
    }

    companion object {
        const val TOKEN_KEY = "tokenKey"
    }
}