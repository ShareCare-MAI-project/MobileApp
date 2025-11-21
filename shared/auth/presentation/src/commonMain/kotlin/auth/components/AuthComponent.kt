package auth.components

import androidx.compose.foundation.text.input.TextFieldState
import kotlinx.coroutines.flow.StateFlow

interface AuthComponent {

    val phoneNumber: TextFieldState
    val OTPCode: TextFieldState

    val currentProgressState: StateFlow<AuthProgressState>

    fun onSendCodeClick()
    fun onVerifyCodeClick()


    fun onBackClick()
}