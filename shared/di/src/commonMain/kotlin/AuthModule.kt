
import ktor.TokenProvider
import ktor.auth.AuthRemoteDataSource
import org.koin.dsl.module
import repositories.AuthRepository
import repositories.AuthRepositoryImpl
import settings.AuthLocalDataSource
import usecases.AuthUseCases
import usecases.extra.RegisterUseCase

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

    factory<RegisterUseCase> {
        RegisterUseCase(get(), get())
    }

    factory<TokenProvider> {
        try {
            TokenProviderImpl(get())
        } catch (_: Throwable) {
            // Крашится из-за того, что AuthRepository зависит от HttpClient,
            // который в свою очередь зависит от TokenProvider,
            // который зависит от AuthRepository.
            // Решение: подождать, когда AuthRepository создастся с NullTokenProviderImpl
            // Позже (при первом запросе) будет использован TokenProviderImpl

            // мда..
            NullTokenProviderImpl()
        }
    }
}


