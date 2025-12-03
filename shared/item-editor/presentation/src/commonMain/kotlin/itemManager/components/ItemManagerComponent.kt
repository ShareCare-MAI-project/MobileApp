package itemManager.components

import androidx.compose.foundation.text.input.TextFieldState
import common.ItemManagerPreData
import kotlinx.coroutines.flow.StateFlow
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import network.NetworkState
import photoTaker.components.PhotoTakerComponent

interface ItemManagerComponent {

    val itemManagerPreData: ItemManagerPreData

    val createItemResult: StateFlow<NetworkState<Unit>>

    val title: TextFieldState
    val description: TextFieldState

    val deliveryTypes: StateFlow<List<DeliveryType>>
    val itemCategory: StateFlow<ItemCategory?>

    fun updateDeliveryType(deliveryType: DeliveryType)
    fun updateItemCategory(itemCategory: ItemCategory)

    fun createItem()


    val photoTakerComponent: PhotoTakerComponent
    val closeFlow: () -> Unit
    val openPhotoTakerComponent: () -> Unit
}