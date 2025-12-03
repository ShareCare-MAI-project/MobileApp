package myProfile.components

import kotlinx.coroutines.flow.StateFlow




interface MyProfileComponent {
    val profileData: StateFlow<QuickProfileData>

    fun logout()
}