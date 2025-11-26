package myProfile.components

import kotlinx.coroutines.flow.StateFlow


data class QuickProfileData(
    val name: String,
    val isVerified: Boolean,
    val organizationName: String?
)

interface MyProfileComponent {
    val profileData: StateFlow<QuickProfileData>
}