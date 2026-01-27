package com.fagundes.myshowlist.core.di

import androidx.room.Room
import com.fagundes.myshowlist.core.data.local.dao.ContentDao
import com.fagundes.myshowlist.core.data.local.datasource.ContentLocalDataSource
import com.fagundes.myshowlist.core.data.local.datasource.ContentLocalDataSourceImpl
import com.fagundes.myshowlist.core.data.remote.api.AnimeApi
import com.fagundes.myshowlist.core.data.remote.api.MovieApi
import com.fagundes.myshowlist.core.data.remote.datasource.ContentRemoteDataSource
import com.fagundes.myshowlist.core.data.remote.datasource.ContentRemoteDataSourceImpl
import com.fagundes.myshowlist.core.data.repository.ContentRepository
import com.fagundes.myshowlist.core.data.repository.ContentRepositoryImpl
import com.fagundes.myshowlist.core.db.AppDatabase
import com.fagundes.myshowlist.core.network.provideJikanHttpClient
import com.fagundes.myshowlist.core.network.provideTmdbHttpClient
import com.fagundes.myshowlist.feat.catalog.vm.CatalogViewModel
import com.fagundes.myshowlist.feat.detail.vm.DetailViewModel
import com.fagundes.myshowlist.feat.home.vm.HomeViewModel
import com.fagundes.myshowlist.feat.login.data.FirebaseAuthRepository
import com.fagundes.myshowlist.feat.login.domain.AuthRepository
import com.fagundes.myshowlist.feat.login.domain.LoginWithGoogleUseCase
import com.fagundes.myshowlist.feat.login.vm.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named


val appModule = module {

    // ---------- Firebase ----------
    single { FirebaseAuth.getInstance() }

    // ---------- Auth ----------
    single<AuthRepository> { FirebaseAuthRepository(get()) }
    factory { LoginWithGoogleUseCase(get()) }

    // ---------- HttpClients ----------
    val tmdbClient = named("TmdbClient")
    val jikanClient = named("JikanClient")

    single(tmdbClient) { provideTmdbHttpClient() }
    single(jikanClient) { provideJikanHttpClient() }

    // ---------- Database ----------
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "myshowlist.db"
        ).build()
    }

    single<ContentDao> { get<AppDatabase>().contentDao() }
    single { Dispatchers.IO }

    // ---------- APIs ----------
    single { MovieApi(get(tmdbClient)) }
    single { AnimeApi(get(jikanClient)) }

    // ---------- Remote DataSource ----------
    single<ContentRemoteDataSource> {
        ContentRemoteDataSourceImpl(
            movieApi = get(),
            animeApi = get()
        )
    }

    // ---------- Local DataSource ----------
    single<ContentLocalDataSource> {
        ContentLocalDataSourceImpl(get())
    }

    // ---------- Repository ----------
    single<ContentRepository> {
        ContentRepositoryImpl(
            remote = get(),
            local = get()
        )
    }

    // ---------- ViewModels ----------
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CatalogViewModel)

    viewModel { (id: String, type: String) ->
        DetailViewModel(
            id = id,
            type = type,
            repository = get()
        )
    }
}
