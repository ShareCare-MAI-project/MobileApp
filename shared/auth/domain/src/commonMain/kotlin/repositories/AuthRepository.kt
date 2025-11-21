package repositories

import kotlinx.coroutines.flow.Flow
import network.NetworkState

interface AuthRepository {
    fun requestCode(phone: String) : Flow<NetworkState<Unit>>
}