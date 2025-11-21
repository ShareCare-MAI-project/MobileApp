package itemManager.components

import androidx.compose.foundation.text.input.TextFieldState
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.retainedSimpleInstance
import decompose.componentCoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import photoTaker.components.PhotoTakerComponent

class RealItemManagerComponent(
    componentContext: ComponentContext,
    override val photoTakerComponent: PhotoTakerComponent,
    override val closeFlow: () -> Unit,
    override val openPhotoTakerComponent: () -> Unit,
) : ComponentContext by componentContext, ItemManagerComponent {

    private val coroutineScope = componentCoroutineScope()
    override val title = retainedSimpleInstance("title") { TextFieldState() }
    override val description = retainedSimpleInstance("desc") { TextFieldState() }


    override val deliveryTypes =
        retainedSimpleInstance("deliveryTypes") { MutableStateFlow(listOf<DeliveryType>()) }


    override val itemCategory =
        retainedSimpleInstance("itemCategory") { MutableStateFlow<ItemCategory?>(null) }


    override fun updateItemCategory(itemCategory: ItemCategory) {
        this.itemCategory.value = itemCategory
    }

    override fun updateDeliveryType(deliveryType: DeliveryType) {
        if (deliveryType in this.deliveryTypes.value) {
            removeDeliveryType(deliveryType)
        } else {
            addDeliveryType(deliveryType)
        }
    }

    private fun addDeliveryType(deliveryType: DeliveryType) {
        this.deliveryTypes.update { current -> current + deliveryType }
    }

    private fun removeDeliveryType(deliveryType: DeliveryType) {
        this.deliveryTypes.update { current -> current - deliveryType }
    }
}