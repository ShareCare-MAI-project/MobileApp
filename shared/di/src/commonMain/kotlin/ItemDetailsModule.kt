
import ktor.ItemDetailsRemoteDataSource
import org.koin.dsl.module
import repositories.ItemDetailsRepository
import repositories.ItemDetailsRepositoryImpl
import usecases.ItemDetailsUseCases


internal val itemDetailsModule = module {
    single<ItemDetailsRemoteDataSource> {
        ItemDetailsRemoteDataSource(
            get(), get()
        )
    }

    single<ItemDetailsRepository> {
        ItemDetailsRepositoryImpl(get())
    }

    factory<ItemDetailsUseCases> {
        ItemDetailsUseCases(get())
    }
}
