package loading.components

import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.retainedSimpleInstance
import decompose.componentCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import network.NetworkState
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.UserUseCases
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class RealLoadingComponent(
    componentContext: ComponentContext,
    private val navigateToRegistration: () -> Unit,
    private val navigateToAuth: () -> Unit
) : LoadingComponent, KoinComponent, ComponentContext by componentContext {
    private val userUseCases: UserUseCases = get()

    private val coroutineScope = componentCoroutineScope()

    init {
        updateUserInfo()
    }

    override fun updateUserInfo() {
        coroutineScope.launchIO {
            // TODO: remove
            delay(4000)
            userUseCases.updateCurrentUserInfo().collect {
                updateUserInfoResult.value = it
            }
            withContext(Dispatchers.Main) {
                updateUserInfoResult.value.onError { result ->
                    if (result.code == 400) { // Не зареган (с токеном всё ок)
                        navigateToRegistration()
                    } else if (result.code == 401) { // Токен умер
                        navigateToAuth()
                    }
                }
            }
        }
    }

    override val updateUserInfoResult = retainedSimpleInstance("loadUserInfoResult") {
        MutableStateFlow<NetworkState<Unit>>(NetworkState.Loading)
    }
    override val helloText: String
        get() = retainedSimpleInstance("helloText") { getNamedHelloText() }

    @OptIn(ExperimentalTime::class)
    fun getNamedHelloText(): String {
        val name = userUseCases.fetchName()
        val currentHour = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).hour
        val dayTime = when (currentHour) {
            in 5..10 -> "Доброе утро"
            in 11..18 -> "Добрый день"
            in 19..21 -> "Добрый вечер"
            else -> "Доброй ночи"
        }

        return name?.let {
            "$dayTime,\n$name!"
        } ?: "$dayTime!"
    }

}