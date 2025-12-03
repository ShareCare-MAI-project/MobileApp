package myProfile.components

import kotlinx.coroutines.flow.StateFlow


interface MyProfileComponent {
    val profileData: StateFlow<QuickProfileData>

    val isHelper: StateFlow<Boolean>

    fun logout()

    fun changeUsuallyI(isHelper: Boolean)
}