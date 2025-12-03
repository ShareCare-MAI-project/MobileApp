package myProfile.components

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.AuthUseCases
import usecases.UserUseCases

class RealMyProfileComponent(
    componentContext: ComponentContext,
    private val goToAuth: () -> Unit
) : MyProfileComponent, KoinComponent, ComponentContext by componentContext {

    private val userUseCases: UserUseCases = get()
    private val authUseCases: AuthUseCases = get()

    override val profileData: StateFlow<QuickProfileData> =
        MutableStateFlow(
            QuickProfileData(
                name = userUseCases.fetchName().toString(),
                isVerified = userUseCases.fetchIsVerified(),
                organizationName = userUseCases.fetchOrganizationName()
            )
        )

    override fun logout() {
        authUseCases.logout()
        goToAuth()
    }

}