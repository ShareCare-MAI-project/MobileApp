package itemManager.components

import androidx.compose.foundation.text.input.TextFieldState
import kotlinx.coroutines.flow.StateFlow
import photoTaker.components.PhotoTakerComponent

interface ItemManagerComponent {

    val title: TextFieldState
    val description: TextFieldState

    val deliveryTypes: StateFlow<List<DeliveryType>>
    val itemCategory: StateFlow<ItemCategory?>

    fun updateDeliveryType(deliveryType: DeliveryType)
    fun updateItemCategory(itemCategory: ItemCategory)



    val photoTakerComponent: PhotoTakerComponent
    val closeFlow: () -> Unit
    val openPhotoTakerComponent: () -> Unit
}