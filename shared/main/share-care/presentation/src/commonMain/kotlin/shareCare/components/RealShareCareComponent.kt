package shareCare.components

import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import decompose.componentCoroutineScope
import entities.ShareCareItems
import kotlinx.coroutines.flow.MutableStateFlow
import network.NetworkState
import network.NetworkState.AFK.onCoroutineDeath
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.ShareCareUseCases

class RealShareCareComponent(
    componentContext: ComponentContext
) : ShareCareComponent, KoinComponent, ComponentContext by componentContext {

    private val shareCareUseCases: ShareCareUseCases = get()
    private val coroutineScope = componentCoroutineScope()


    override val items: MutableStateFlow<NetworkState<ShareCareItems>> =
        MutableStateFlow(NetworkState.AFK)


    init {
        fetchItems()
    }

    override fun fetchItems() {
        coroutineScope.launchIO {
            shareCareUseCases.fetchItems().collect {
                items.value = it
            }
        }.invokeOnCompletion {
            items.value = items.value.onCoroutineDeath()
        }
    }
}