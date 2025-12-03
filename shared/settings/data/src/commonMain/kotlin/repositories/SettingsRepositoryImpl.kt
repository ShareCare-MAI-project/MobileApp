package repositories

import enums.UsuallyI
import local.SettingsLocalDataSource

class SettingsRepositoryImpl(
    private val localDataSource: SettingsLocalDataSource
) : SettingsRepository {
    override fun changeUsuallyI(usuallyI: UsuallyI) = localDataSource.changeUsuallyI(usuallyI)
    override fun fetchUsuallyI(): UsuallyI = localDataSource.fetchUsuallyI()
}