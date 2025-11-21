package ktor.bodies

import kotlinx.serialization.Serializable

@Serializable
data class RequestCodeBody(
    val phone: String
)