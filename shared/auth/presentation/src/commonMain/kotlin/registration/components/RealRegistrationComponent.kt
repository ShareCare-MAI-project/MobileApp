package registration.components

import androidx.compose.foundation.text.input.TextFieldState
import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.retainedSimpleInstance
import decompose.componentCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import network.NetworkState
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.AuthUseCases
import usecases.extra.RegisterUseCase

class RealRegistrationComponent(
    componentContext: ComponentContext,
    val goMain: () -> Unit
) : RegistrationComponent, KoinComponent, ComponentContext by componentContext {

    val coroutineScope = componentCoroutineScope()

    val authUseCases: AuthUseCases = get()
    val registerUseCase: RegisterUseCase = get()

    override val name: TextFieldState = retainedSimpleInstance("name") { TextFieldState() }
    override val telegram: TextFieldState = retainedSimpleInstance("telegram") { TextFieldState() }
    override val registrationResult  =
        MutableStateFlow<NetworkState<Unit>>(NetworkState.AFK)


    override fun onRegistrationClick() {
        if (!registrationResult.value.isLoading()) {
            coroutineScope.launchIO {
                registerUseCase(
                    name = name.text.toString(),
                    telegram = telegram.text.toString()
                ).collect {
                    registrationResult.value = it
                }
                withContext(Dispatchers.Main) {
                    registrationResult.value.handle {
                        goMain()
                    }
                }
            }
        }
    }
}