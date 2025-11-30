package ktor

import dto.ItemDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import network.NetworkState

class ItemEditorRemoteDataSource(
    private val hc: HttpClient,
    private val tokenProvider: TokenProvider
) {

    fun createItem(item: ItemDTO): Flow<NetworkState<Unit>> =
        hc.defaultRequest {
            val token = tokenProvider.getToken()
            hc.submitFormWithBinaryData(
                url = CREATE_ITEM_PATH,
                formData = formData {
                    append("item_data", Json.encodeToString(item.copy(images = emptyList())))

                    // Добавляем изображения отдельно
                    item.images.forEachIndexed { index, imageData ->
                        append(
                            "images",
                            imageData,
                            Headers.build {
                                append(
                                    "Content-Disposition",
                                    "form-data; name=\"images\"; filename=\"image$index.jpg\""
                                )
                                append("Content-Type", "image/jpeg")
                            }
                        )
                    }
                }
            ) {
                contentType(ContentType.Application.Json)
                token?.let { bearerAuth(token) }
            }
        }


    private companion object {
        const val PRE_PATH = "/items"

        const val CREATE_ITEM_PATH = "$PRE_PATH/"
    }
}