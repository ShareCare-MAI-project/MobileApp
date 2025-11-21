package network

sealed class NetworkState<out T> {
    object AFK : NetworkState<Nothing>()
    object Loading : NetworkState<Nothing>()
    data class Success<T>(val data: T) : NetworkState<T>()
    data class Error(val error: Throwable) : NetworkState<Nothing>()


    fun isLoading() =
        this is Loading

}