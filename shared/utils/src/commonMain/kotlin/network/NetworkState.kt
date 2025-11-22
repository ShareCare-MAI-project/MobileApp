package network

sealed class NetworkState<out T> {
    object AFK : NetworkState<Nothing>()
    object Loading : NetworkState<Nothing>()
    data class Success<T>(val data: T) : NetworkState<T>()
    data class Error(val error: Throwable, val prettyPrint: String) : NetworkState<Nothing>()


    fun isLoading() =
        this is Loading
    fun isErrored() =
        this is Error

    inline fun <R> defaultWhen(onSuccess: (Success<out T>) -> NetworkState<R>) = when(this) {
        AFK -> AFK
        is Error -> this
        Loading -> Loading
        is Success -> onSuccess(this)
    }
}