package auth.components

import androidx.compose.foundation.text.input.TextFieldState
import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import decompose.componentCoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class RealAuthComponent(
    componentContext: ComponentContext,
) : AuthComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()

    override val phoneNumber: TextFieldState = TextFieldState("+7")
    override val OTPCode: TextFieldState = TextFieldState()
    override val currentProgressState = MutableStateFlow(AuthProgressState.PHONE)

    override fun onSendCodeClick() {
        this.currentProgressState.value = AuthProgressState.OTPCode
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