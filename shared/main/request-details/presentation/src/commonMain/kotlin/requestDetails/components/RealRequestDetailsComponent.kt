package requestDetails.components

import com.arkivanov.decompose.ComponentContext
import org.koin.core.component.KoinComponent

class RealRequestDetailsComponent(
    componentContext: ComponentContext,
    override val id: String,
    override val creatorId: String,
    override val currentId: String,

    override val onBackClick: () -> Unit,
) : RequestDetailsComponent, KoinComponent, ComponentContext by componentContext {
}