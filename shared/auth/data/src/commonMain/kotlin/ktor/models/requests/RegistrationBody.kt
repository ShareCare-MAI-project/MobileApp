package ktor.models.requests

import kotlinx.serialization.Serializable


@Serializable
data class RegistrationBody(
    val name: String,
    val telegram: String
)