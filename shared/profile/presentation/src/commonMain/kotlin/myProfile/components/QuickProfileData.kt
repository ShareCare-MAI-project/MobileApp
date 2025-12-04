package myProfile.components

import kotlinx.serialization.Serializable

@Serializable
data class QuickProfileData(
    val name: String,
    val isVerified: Boolean,
    val organizationName: String?
)