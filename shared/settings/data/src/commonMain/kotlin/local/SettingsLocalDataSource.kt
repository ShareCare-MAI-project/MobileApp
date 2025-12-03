package local

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import enums.UsuallyI

class SettingsLocalDataSource(
    val settings: Settings
) {

    fun changeUsuallyI(usuallyI: UsuallyI) {
        settings[USUALLY_I_KEY] = usuallyI.name
    }

    fun fetchUsuallyI(): UsuallyI =
        UsuallyI.valueOf(settings[USUALLY_I_KEY, UsuallyI.FindHelp.name])


    companion object {
        const val USUALLY_I_KEY = "usuallyIKey"
    }
}