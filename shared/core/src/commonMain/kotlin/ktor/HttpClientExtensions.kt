package ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import network.NetworkState

inline fun <reified T> HttpClient.defaultPost(
    path: String,
    body: Any? = null,
    crossinline block: HttpRequestBuilder.() -> Unit = {}
): Flow<NetworkState<T>> = flow {
    emit(NetworkState.Loading)

    try {
        val response = post {
            url {
                contentType(ContentType.Application.Json)
                path(path)
                body?.let { setBody(it) }
                block()
            }
        }

        when {
            response.status.isSuccess() -> {
                val responseBody = response.body<T>()
                emit(NetworkState.Success(responseBody))
            }

            else -> {
                emit(NetworkState.Error(Throwable("${response.status}: ${response.bodyAsText()}")))
            }
        }
    } catch (e: Exception) {
        emit(NetworkState.Error(e))
    }

}.flowOn(Dispatchers.IO)
//
//suspend fun HttpResponse.throwIfNoOK() {
//    if (this.status != HttpStatusCode.OK) {
//        throw NoOkException(
//            "${this.status.value} ${
//                this.call.request.url.encodedPath.removeSuffix("/").removePrefix("/")
//            }\n${this.bodyAsText()}"
//        )
//    }
//}