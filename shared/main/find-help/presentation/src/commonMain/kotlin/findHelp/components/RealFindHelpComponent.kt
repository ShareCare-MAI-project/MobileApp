package findHelp.components

import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import common.detailsInterfaces.DetailsConfig
import common.search.SearchData
import decompose.componentCoroutineScope
import entities.FindHelpBasic
import entity.ItemResponse
import entity.SearchRequest
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import network.NetworkState
import network.NetworkState.AFK.onCoroutineDeath
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


    override val items: MutableStateFlow<NetworkState<List<ItemResponse>>> =
        MutableStateFlow(NetworkState.AFK)
    override val searchHasMoreItems: MutableStateFlow<Boolean> =
        MutableStateFlow(true)

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


    override val searchData: MutableStateFlow<SearchData> = MutableStateFlow(
        SearchData(category = null, deliveryTypes = listOf(), query = "")
    )

    override fun onQueryChange(query: String) {
        searchData.value = searchData.value.copy(query = query)
        onSearch(resetItems = true)
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
        if (!items.value.isLoading() && searchJob?.isActive != true) {
            if (resetItems) searchHasMoreItems.value = true

            val data = searchData.value
            val searchRequest = SearchRequest(
                query = data.query,
                category = data.category,
                deliveryTypes = data.deliveryTypes,
                offset = if (items.value is NetworkState.Success && !resetItems) (items.value as NetworkState.Success<List<ItemResponse>>).data.size else 0,
                toLoad = 2
            )
            searchJob = coroutineScope.launchIO {
                findHelpUseCases.search(searchRequest).collect { response ->
                    if (items.value is NetworkState.Success) {
                        response.handle { success ->
                            if (resetItems) {
                                items.value = success
                            } else {
                                items.value =
                                    NetworkState.Success((items.value as NetworkState.Success<List<ItemResponse>>).data + success.data)
                            }
                        }
                    } else {
                        items.value = response
                    }
                    response.handle { success ->
                        if (success.data.isEmpty()) searchHasMoreItems.value = false
                    }
                }
            }.also {
                it.invokeOnCompletion { items.value = items.value.onCoroutineDeath() }
            }
        }
    }
}