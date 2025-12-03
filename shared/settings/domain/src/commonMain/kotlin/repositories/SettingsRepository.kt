package repositories

import enums.UsuallyI


interface SettingsRepository {
    fun changeUsuallyI(usuallyI: UsuallyI)
    fun fetchUsuallyI(): UsuallyI
}