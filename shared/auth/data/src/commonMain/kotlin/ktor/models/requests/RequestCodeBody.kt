package ktor.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class RequestCodeBody(
    val phone: String
)