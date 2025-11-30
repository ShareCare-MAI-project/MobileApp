import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    enableLogging: Boolean,
    appDeclaration: KoinAppDeclaration = {}
): KoinApplication {
    return startKoin {
        appDeclaration()
        modules(
            coreModule(enableLogging),
            authModule,
            userModule,
            itemEditorModule,
            requestDetailsModule,
            shareCareModule,
            findHelpModule,
        )
    }
}