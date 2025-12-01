package shareCare.components

import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import common.detailsInterfaces.DetailsConfig
import common.search.SearchData
import decompose.componentCoroutineScope
import entities.ShareCareItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import network.NetworkState
import network.NetworkState.AFK.onCoroutineDeath
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.ShareCareUseCases

class RealShareCareComponent(
    componentContext: ComponentContext,
    override val openDetails: (cfg: DetailsConfig) -> Unit
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

    override val searchData: MutableStateFlow<SearchData> =
        MutableStateFlow(SearchData())

    override fun onQueryChange(query: String) {
        searchData.value = searchData.value.copy(query = query)
        onSearch()
    }

    override fun onSearch() {
    }
}