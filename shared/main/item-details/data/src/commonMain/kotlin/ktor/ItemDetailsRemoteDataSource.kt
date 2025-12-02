package ktor

import dto.TakeItemResponseDTO
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import network.NetworkState

class ItemDetailsRemoteDataSource(
    private val hc: HttpClient,
    private val tokenProvider: TokenProvider
) {

    fun takeItem(itemId: String): Flow<NetworkState<TakeItemResponseDTO>> =
        hc.defaultPatch("$TAKE_ITEM_PATH/$itemId", tokenProvider)

    fun denyItem(itemId: String): Flow<NetworkState<Unit>> =
        hc.defaultPatch("$DENY_ITEM_PATH/$itemId", tokenProvider)

    private companion object {
        const val TAKE_ITEM_PATH = "findhelp/take"
        const val DENY_ITEM_PATH = "items/deny"
    }

}