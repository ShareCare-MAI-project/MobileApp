package network

import kotlinx.coroutines.CancellationException

sealed class NetworkState<out T> {
    object AFK : NetworkState<Nothing>()
    object Loading : NetworkState<Nothing>()
    data class Success<T>(val data: T) : NetworkState<T>()
    data class Error(val error: Throwable, val prettyPrint: String, val code: Int) :
        NetworkState<Nothing>()


    fun <R> NetworkState<R>.onCoroutineDeath(initialState: NetworkState<R> = Error(CancellationException(""), "coroutineDeath", 0)) =
        if (isLoading()) AFK else this


    fun isLoading() =
        this is Loading

    fun isAFK() =
        this is AFK

    fun isErrored() =
        this is Error

    inline fun <R> defaultWhen(onSuccess: (Success<out T>) -> NetworkState<R>) = when (this) {
        AFK -> AFK
        is Error -> this
        Loading -> Loading
        is Success -> onSuccess(this)
    }

    fun onError(onError: (Error) -> Unit) {
        if (this is Error) onError(this)
    }

    fun handle(onError: (Error) -> Unit = {}, onSuccess: (Success<out T>) -> Unit = {}) {
        if (this is Success) {
            onSuccess(this)
        } else if (this is Error) {
            onError(this)
        }
    }


}