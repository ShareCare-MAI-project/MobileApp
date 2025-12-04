package myProfile.components

import kotlinx.coroutines.flow.StateFlow


interface MyProfileComponent {
    val profileData: StateFlow<QuickProfileData>

    val isHelper: StateFlow<Boolean>

    fun logout()
    val goToMain: () -> Unit

    fun changeUsuallyI(isHelper: Boolean)

    fun changeFontSize(value: Float)
}