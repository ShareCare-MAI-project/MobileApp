package itemManager.components

import androidx.compose.foundation.text.input.TextFieldState
import entities.enums.DeliveryType
import entities.enums.ItemCategory
import kotlinx.coroutines.flow.StateFlow
import network.NetworkState
import photoTaker.components.PhotoTakerComponent

interface ItemManagerComponent {

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