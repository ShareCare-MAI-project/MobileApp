package findHelp.components

import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import common.detailsInterfaces.DetailsConfig
import decompose.componentCoroutineScope
import entities.FindHelpBasic
import kotlinx.coroutines.flow.MutableStateFlow
import network.NetworkState
import network.NetworkState.AFK.onCoroutineDeath
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.FindHelpUseCases

class RealFindHelpComponent(
    componentContext: ComponentContext,
    override val openDetails: (cfg: DetailsConfig) -> Unit
) : FindHelpComponent, KoinComponent, ComponentContext by componentContext {
    private val findHelpUseCases: FindHelpUseCases = get()
    private val coroutineScope = componentCoroutineScope()

    override val basic: MutableStateFlow<NetworkState<FindHelpBasic>> =
        MutableStateFlow(NetworkState.AFK)

    init {
        fetchBasic()
    }

    override fun fetchBasic() {
        coroutineScope.launchIO {
            findHelpUseCases.fetchBasic().collect {
                basic.value = it
            }
        }.invokeOnCompletion {
            basic.value = basic.value.onCoroutineDeath()
        }
    }
}