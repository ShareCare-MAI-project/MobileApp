package myProfile.components

import com.arkivanov.decompose.ComponentContext
import org.koin.core.component.KoinComponent

class RealMyProfileComponent(
    componentContext: ComponentContext
): MyProfileComponent, KoinComponent, ComponentContext by componentContext {
}