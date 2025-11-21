package auth.components

import androidx.compose.foundation.text.input.TextFieldState
import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import decompose.componentCoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import network.NetworkState
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.AuthUseCases

class RealAuthComponent(
    componentContext: ComponentContext
) : AuthComponent, KoinComponent, ComponentContext by componentContext {


    private val authUseCases: AuthUseCases = this.get()

    private val coroutineScope = componentCoroutineScope()

    override val phoneNumber: TextFieldState = TextFieldState("+7")
    override val OTPCode: TextFieldState = TextFieldState()


    override val requestCodeResult: MutableStateFlow<NetworkState<Unit>> =
        MutableStateFlow(NetworkState.AFK)

    override val currentProgressState = MutableStateFlow(AuthProgressState.PHONE)

    override fun onSendCodeClick() {
        coroutineScope.launchIO {
            authUseCases.requestCode(phoneNumber.text.toString()).collect {
                requestCodeResult.value = it
            }

            if (requestCodeResult.value is NetworkState.Success)
                currentProgressState.value = AuthProgressState.OTPCode

        }
    }

    override fun onVerifyCodeClick() {
        coroutineScope.launchIO {
            // some logic
        }
    }

    override fun onBackClick() {
        when (currentProgressState.value) {
            AuthProgressState.PHONE -> {}
            AuthProgressState.OTPCode -> {
                this.currentProgressState.value = AuthProgressState.PHONE
            }
        }
    }
}