package profileFlow.components

import architecture.DefaultStack
import kotlinx.serialization.Serializable
import myProfile.components.MyProfileComponent
import profileFlow.components.ProfileFlowComponent.Child
import profileFlow.components.ProfileFlowComponent.Config

interface ProfileFlowComponent : DefaultStack<Config, Child>  {

    sealed interface Child {
        data class MyProfileChild(val myProfileComponent: MyProfileComponent) :
            Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object MyProfile : Config
    }


    sealed class Output {
        data object NavigateToAuth : Output()
    }
}