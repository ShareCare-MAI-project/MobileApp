package registration.components

import com.arkivanov.decompose.ComponentContext
import org.koin.core.component.KoinComponent

class RealRegistrationComponent(
    componentContext: ComponentContext
) : RegistrationComponent, KoinComponent, ComponentContext by componentContext {

}