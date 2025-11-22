import ktor.AuthRemoteDataSource
import org.koin.dsl.module
import repositories.AuthRepository
import repositories.AuthRepositoryImpl
import settings.AuthLocalDataSource
import usecases.AuthUseCases

internal val authModule = module {
    single<AuthRemoteDataSource> {
        AuthRemoteDataSource(
            get()
        )
    }
    single<AuthLocalDataSource> {
        AuthLocalDataSource(
            get()
        )
    }

    single<AuthRepository> {
        AuthRepositoryImpl(get(), get())
    }



    factory<AuthUseCases> {
        AuthUseCases(get())
    }
}