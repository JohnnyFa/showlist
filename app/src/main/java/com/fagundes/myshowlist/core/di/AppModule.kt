package com.fagundes.myshowlist.core.di

import android.util.Log
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
import com.fagundes.myshowlist.feat.catalog.vm.CatalogViewModel
import com.fagundes.myshowlist.feat.detail.vm.DetailViewModel
import com.fagundes.myshowlist.feat.home.vm.HomeViewModel
import com.fagundes.myshowlist.feat.login.data.FirebaseAuthRepository
import com.fagundes.myshowlist.feat.login.domain.AuthRepository
import com.fagundes.myshowlist.feat.login.domain.LoginWithGoogleUseCase
import com.fagundes.myshowlist.feat.login.vm.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import io.ktor.client.plugins.logging.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel


val appModule = module {

    // ---------- Firebase ----------
    single {
        FirebaseAuth.getInstance()
    }

    // ---------- Auth ----------
    single<AuthRepository> {
        FirebaseAuthRepository(get())
    }

    factory {
        LoginWithGoogleUseCase(get())
    }

    // ---------- Ktor HttpClient ----------
    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                    }
                )
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("Ktor", message)
                    }
                }
            }
        }
    }

    // ---------- Database ----------
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "myshowlist.db"
        ).build()
    }

    single<ContentDao> {
        get<AppDatabase>().contentDao()
    }

    single { Dispatchers.IO }

    // ---------- APIs ----------
    single {
        MovieApi(get())
    }

    single {
        AnimeApi(get())
    }

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

    // ---------- Catalog Repository ----------
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
