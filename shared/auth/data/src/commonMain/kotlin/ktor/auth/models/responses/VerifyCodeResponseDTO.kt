import kotlinx.serialization.Serializable

@Serializable
data class VerifyCodeResponseDTO(
    val token: String,
    val name: String?
)