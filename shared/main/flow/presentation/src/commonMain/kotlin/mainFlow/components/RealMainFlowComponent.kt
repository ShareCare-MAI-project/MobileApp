package mainFlow.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import common.detailsInterfaces.DetailsComponent
import common.detailsInterfaces.DetailsConfig
import common.detailsInterfaces.DetailsConfig.ItemDetailsConfig
import findHelp.components.RealFindHelpComponent
import itemDetails.components.RealItemDetailsComponent
import loading.components.LoadingComponent
import loading.components.RealLoadingComponent
import mainFlow.components.MainFlowComponent.Child
import mainFlow.components.MainFlowComponent.Child.FindHelpChild
import mainFlow.components.MainFlowComponent.Child.ShareCareChild
import mainFlow.components.MainFlowComponent.Config
import mainFlow.components.MainFlowComponent.Output
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import requestDetails.components.RealRequestDetailsComponent
import shareCare.components.RealShareCareComponent
import usecases.AuthUseCases

class RealMainFlowComponent(
    componentContext: ComponentContext,
    override val output: (Output) -> Unit
) : MainFlowComponent, KoinComponent, ComponentContext by componentContext {

    private val authUseCases: AuthUseCases = get()

    override val loadingComponent: LoadingComponent =
        RealLoadingComponent(
            componentContext.childContext("loadingComponent"),
            navigateToAuth = { output(Output.NavigateToAuth) },
            navigateToRegistration = { output(Output.NavigateToRegistration) }
        )

    override val nav = StackNavigation<Config>()


    private val _stack =
        childStack(
            source = nav,
            serializer = Config.serializer(),
            initialConfiguration = calculateInitialConfig(),
            childFactory = ::child,
        )

    override val stack: Value<ChildStack<Config, Child>>
        get() = _stack

    override val detailsNav = SlotNavigation<DetailsConfig>()
    private val _detailsSlot: Value<ChildSlot<*, DetailsComponent>> =
        childSlot(
            source = detailsNav,
            serializer = DetailsConfig.serializer(),
            handleBackButton = true,
            childFactory = { cfg, ctx ->
                val currentId = authUseCases.fetchUserId().toString()
                when (cfg) {
                    is ItemDetailsConfig ->
                        RealItemDetailsComponent(
                            ctx,
                            id = cfg.id,
                            images = cfg.images,
                            currentId = currentId,
                            creatorId = cfg.creatorId,
                            title = cfg.title,
                            description = cfg.description,
                            location = cfg.location,
                            category = cfg.category,
                            deliveryTypes = cfg.deliveryTypes,
                            recipientId = cfg.recipientId
                        )

                    is DetailsConfig.RequestDetailsConfig ->
                        RealRequestDetailsComponent(
                            ctx,
                            id = cfg.id,
                            currentId = currentId,
                            creatorId = "TODO"
                        ) {
                            detailsNav.dismiss()
                        }
                }
            }

        )


    override val detailsSlot: Value<ChildSlot<*, DetailsComponent>>
        get() = _detailsSlot


    private fun openDetails(cfg: DetailsConfig) {
        detailsNav.activate(cfg)
    }

    private fun child(config: Config, childContext: ComponentContext): Child {
        return when (config) {
            Config.FindHelp -> FindHelpChild(
                RealFindHelpComponent(
                    childContext, openDetails = ::openDetails
                )
            )

            Config.ShareCare -> ShareCareChild(
                RealShareCareComponent(childContext, openDetails = ::openDetails)
            )
        }
    }

    override fun onBackClicked() {
        val activeCfg = _stack.active.configuration
        nav.bringToFront(
            when (activeCfg) {
                Config.FindHelp -> Config.ShareCare
                Config.ShareCare -> Config.FindHelp
            }
        )
    }
//        if (activeCfg != initialStack.first()) {
//
//        } else {
//            super.onBackClicked() TODO exit app
//        }


    private fun calculateInitialConfig(): Config {
        return Config.FindHelp
    }


    override fun navigateTo(cfg: Config) {
        nav.bringToFront(cfg)
    }
}