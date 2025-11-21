package auth.components

import androidx.compose.foundation.text.input.TextFieldState
import kotlinx.coroutines.flow.StateFlow
import network.NetworkState

interface AuthComponent {

    val phoneNumber: TextFieldState
    val OTPCode: TextFieldState

    val currentProgressState: StateFlow<AuthProgressState>

    val requestCodeResult: StateFlow<NetworkState<Unit>>

    fun onSendCodeClick()
    fun onVerifyCodeClick()


    fun onBackClick()
}