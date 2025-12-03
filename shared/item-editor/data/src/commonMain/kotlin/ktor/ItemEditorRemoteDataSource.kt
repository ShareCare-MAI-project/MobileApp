package ktor

import dto.ItemDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.parameter
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
                url = ITEMS_PATH,
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

    fun editItem(item: ItemDTO, itemId: String): Flow<NetworkState<Unit>> =
        hc.defaultPatch(ITEMS_PATH, tokenProvider, body = item) {
            parameter("item_id", itemId)
        }


    private companion object {
        const val ITEMS_PATH = "/items/"
    }
}