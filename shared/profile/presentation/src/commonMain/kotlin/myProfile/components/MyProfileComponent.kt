package myProfile.components

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.value.Value
import interfaces.DialogComponent
import interfaces.DialogConfig
import kotlinx.coroutines.flow.StateFlow


interface MyProfileComponent {

    val dialogsNav: SlotNavigation<DialogConfig>
    val dialogsSlot: Value<ChildSlot<*, DialogComponent>>


    val profileData: StateFlow<QuickProfileData>

    val isHelper: StateFlow<Boolean>

    fun logout()
    val goToMain: () -> Unit

    fun changeUsuallyI(isHelper: Boolean)

    fun changeFontSize(value: Float)
}