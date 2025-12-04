package myProfile.components

import FontSizeManager
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import editProfile.components.RealEditProfileComponent
import enums.UsuallyI
import interfaces.DialogComponent
import interfaces.DialogConfig
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.AuthUseCases
import usecases.SettingsUseCases
import usecases.UserUseCases

class RealMyProfileComponent(
    componentContext: ComponentContext,
    private val goToAuth: () -> Unit,
    override val goToMain: () -> Unit,
) : MyProfileComponent, KoinComponent, ComponentContext by componentContext {

    private val userUseCases: UserUseCases = get()
    private val authUseCases: AuthUseCases = get()
    private val settingsUseCases: SettingsUseCases = get()


    override val dialogsNav = SlotNavigation<DialogConfig>()
    override val dialogsSlot: Value<ChildSlot<*, DialogComponent>> =
        childSlot(
            source = dialogsNav,
            serializer = DialogConfig.serializer(),
            handleBackButton = true,
            childFactory = { cfg, ctx ->
                when (cfg) {
                    is DialogConfig.EditProfile -> RealEditProfileComponent(
                        ctx, initialName = profileData.value.name,
                        onBackClick = { dialogsNav.dismiss() },
                        onNameChange = { name ->
                            profileData.value = profileData.value.copy(name = name)
                        }
                    )

                    is DialogConfig.Verification -> TODO()
                }
            }
        )


    override val profileData: MutableStateFlow<QuickProfileData> =
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