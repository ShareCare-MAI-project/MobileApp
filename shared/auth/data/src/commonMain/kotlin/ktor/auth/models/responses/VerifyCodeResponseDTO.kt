import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyCodeResponseDTO(
    val token: String,
    @SerialName("user_id") val userId: String,
    val name: String?,
)