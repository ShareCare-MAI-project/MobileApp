package interfaces

import kotlinx.serialization.Serializable

@Serializable
sealed interface DialogConfig {
    data class Verification(
        val isVerified: Boolean,
        val organizationName: String?
    ) : DialogConfig

    data class EditProfile(
        val name: String
    ) : DialogConfig
}