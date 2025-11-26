package ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json


// sry
// TokenProvider cуществует, чтобы пробрасывать токен в core модуль
// Поэтому я не могу использовать FetchTokenUseCase, т.к. это приведёт к CircularDependency
interface TokenProvider {
    fun getToken(): String?
}

@OptIn(ExperimentalSerializationApi::class)
internal fun createHttpClient(
    enableLogging: Boolean,
    tokenProvider: TokenProvider
): HttpClient {
    return HttpClient(CIO) {

        if (enableLogging) {
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }


        install(HttpTimeout) {
            connectTimeoutMillis = 15000
            requestTimeoutMillis = 30000
        }

        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    prettyPrint = true
                }, contentType = ContentType.Any
            )
        }



        defaultRequest {
            header("Content-Type", "application/json; charset=UTF-8")
            tokenProvider.getToken()?.let { token ->
                bearerAuth(token)
            }

            contentType(ContentType.Application.Json)
            url {
                host = "10.0.2.2" // `localhost` but from android studio emulator
//                host = "10.230.221.145" // `localhost` but from device and wifi hotspot
                protocol = URLProtocol.HTTP
            }
            port = 8080
        }
    }
}