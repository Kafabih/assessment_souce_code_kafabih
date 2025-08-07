package app.svck.githubuserapp.di

import androidx.room.Room
import app.svck.githubuserapp.data.local.AppDatabase
import app.svck.githubuserapp.data.remote.GithubApiService
import app.svck.githubuserapp.data.repository.UserRepository
import app.svck.githubuserapp.domain.usecase.GetCachedUsersUseCase
import app.svck.githubuserapp.domain.usecase.GetUserDetailsUseCase
import app.svck.githubuserapp.domain.usecase.SearchUsersUseCase
import app.svck.githubuserapp.ui.presentation.viewmodel.UserSearchViewModel
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    // Singletons for Database, and DAO
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "github_user_search.db"
        ).build()
    }
    single { get<AppDatabase>().userDao() }

    // Networking setup
    single { ChuckerInterceptor(androidContext()) }
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<ChuckerInterceptor>())
            .build()
    }
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(get<OkHttpClient>())
            .addConverterFactory(MoshiConverterFactory.create(get<Moshi>()))
            .build()
            .create(GithubApiService::class.java)
    }

    // Singleton for the Repository
    single { UserRepository(get(), get()) }

    // Factories for UseCases
    factory { SearchUsersUseCase(get()) }
    factory { GetUserDetailsUseCase(get()) }
    factory { GetCachedUsersUseCase(get()) }

    // ViewModel
    viewModel { UserSearchViewModel(get(), get(), get()) }
}
