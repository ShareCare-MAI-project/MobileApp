package ktor

import dto.ItemDTO
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import network.NetworkState

class ItemEditorRemoteDataSource(
    private val hc: HttpClient,
    private val tokenProvider: TokenProvider
) {

    fun createItem(item: ItemDTO): Flow<NetworkState<Unit>> =
        hc.defaultPost(path = CREATE_ITEM_PATH, body = item, tokenProvider = tokenProvider)


    private companion object {
        const val PRE_PATH = "/items"

        const val CREATE_ITEM_PATH = "$PRE_PATH/"
    }
}