package findHelp.components

import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import common.detailsInterfaces.DetailsConfig
import common.search.SearchData
import common.search.defaultOnSearch
import decompose.componentCoroutineScope
import entities.FindHelpBasic
import entity.ItemResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import network.NetworkState
import network.NetworkState.AFK.onCoroutineDeath
import network.NetworkState.AFK.saveState
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.AuthUseCases
import usecases.FindHelpUseCases

class RealFindHelpComponent(
    componentContext: ComponentContext,
    override val openDetails: (cfg: DetailsConfig) -> Unit
) : FindHelpComponent, KoinComponent, ComponentContext by componentContext {
    private val findHelpUseCases: FindHelpUseCases = get()
    private val authUseCases: AuthUseCases = get()
    private val coroutineScope = componentCoroutineScope()
    override val myId: String = authUseCases.fetchUserId()!!

    override val basic: MutableStateFlow<NetworkState<FindHelpBasic>> =
        MutableStateFlow(NetworkState.AFK)


    init {
        fetchBasic()
    }

    override fun fetchBasic() {
        coroutineScope.launchIO {
            findHelpUseCases.fetchBasic().collect {
                val prevData = basic.value.data
                basic.value = it.saveState(prevData)
            }
        }.invokeOnCompletion {
            basic.value = basic.value.onCoroutineDeath()
        }
    }


    override val items: MutableStateFlow<NetworkState<List<ItemResponse>>> =
        MutableStateFlow(NetworkState.AFK)
    override val searchHasMoreItems: MutableStateFlow<Boolean> =
        MutableStateFlow(true)
    override val searchData: MutableStateFlow<SearchData> = MutableStateFlow(
        SearchData(category = null, deliveryTypes = listOf(), query = "")
    )

    override fun onQueryChange(query: String) {
        if (query != searchData.value.query) {
            searchData.value = searchData.value.copy(query = query)
            onSearch(resetItems = true)
        }
    }

    override fun onCategoryChange(category: ItemCategory?) {
        searchData.value = searchData.value.copy(category = category)
        onSearch(resetItems = true)
    }

    override fun onDeliveryTypesChange(deliveryTypes: List<DeliveryType>) {
        searchData.value = searchData.value.copy(deliveryTypes = deliveryTypes)
        onSearch(resetItems = true)
    }

    private var searchJob: Job? = null
    override fun onSearch(resetItems: Boolean) {
        searchJob = defaultOnSearch(
            items = items,
            searchHasMoreItems = searchHasMoreItems,
            searchData = searchData.value,
            coroutineScope = coroutineScope,
            prevJob = searchJob,
            toLoad = 2,
            resetItems = resetItems
        ) { findHelpUseCases.search(it) } ?: searchJob
    }
}