package myProfile.components

import FontSizeManager
import com.arkivanov.decompose.ComponentContext
import enums.UsuallyI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.AuthUseCases
import usecases.SettingsUseCases
import usecases.UserUseCases

class RealMyProfileComponent(
    componentContext: ComponentContext,
    private val goToAuth: () -> Unit
) : MyProfileComponent, KoinComponent, ComponentContext by componentContext {

    private val userUseCases: UserUseCases = get()
    private val authUseCases: AuthUseCases = get()
    private val settingsUseCases: SettingsUseCases = get()

    override val profileData: StateFlow<QuickProfileData> =
        MutableStateFlow(
            QuickProfileData(
                name = userUseCases.fetchName().toString(),
                isVerified = userUseCases.fetchIsVerified(),
                organizationName = userUseCases.fetchOrganizationName()
            )
        )
    override val isHelper: MutableStateFlow<Boolean> =
        MutableStateFlow(settingsUseCases.fetchUsuallyI() == UsuallyI.ShareCare)

    override fun logout() {
        authUseCases.logout()
        goToAuth()
    }

    override fun changeUsuallyI(isHelper: Boolean) {
        val usuallyI = if (isHelper) UsuallyI.ShareCare else UsuallyI.FindHelp
        settingsUseCases.changeUsuallyI(usuallyI)
        this.isHelper.value = isHelper
    }

    override fun changeFontSize(value: Float) {
        settingsUseCases.changeFontSize(value)
        FontSizeManager.setFontSize(value)
    }

}