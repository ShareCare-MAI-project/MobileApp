package myProfile.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.retainedSimpleInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.UserUseCases

class RealMyProfileComponent(
    componentContext: ComponentContext
) : MyProfileComponent, KoinComponent, ComponentContext by componentContext {

    private val userUseCases: UserUseCases = get()

    override val profileData: StateFlow<QuickProfileData> =
        MutableStateFlow(
            QuickProfileData(
                name = userUseCases.fetchName().toString(),
                isVerified = userUseCases.fetchIsVerified(),
                organizationName = userUseCases.fetchOrganizationName()
            )
        )

}