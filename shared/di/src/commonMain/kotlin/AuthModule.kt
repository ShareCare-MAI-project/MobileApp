import ktor.AuthRemoteDataSource
import org.koin.dsl.module
import repositories.AuthRepository
import repositories.AuthRepositoryImpl
import usecases.AuthUseCases

internal val authModule = module {
    single<AuthRemoteDataSource> {
        AuthRemoteDataSource(
            get()
        )
    }

    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }



    factory<AuthUseCases> {
        AuthUseCases(get())
    }
}