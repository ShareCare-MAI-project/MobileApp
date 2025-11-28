package repositories

import entities.FindHelpBasic
import kotlinx.coroutines.flow.Flow
import network.NetworkState

interface FindHelpRepository {
    fun fetchBasic(): Flow<NetworkState<FindHelpBasic>>
}