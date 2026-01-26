package com.fagundes.myshowlist.core.di

import android.util.Log
import com.fagundes.myshowlist.core.data.remote.api.AnimeApi
import com.fagundes.myshowlist.core.data.remote.api.MovieApi
import com.fagundes.myshowlist.core.data.repository.ContentRepository
import com.fagundes.myshowlist.core.data.repository.ContentRepositoryImpl
import com.fagundes.myshowlist.feat.catalog.vm.CatalogViewModel
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
import kotlinx.serialization.json.Json


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

    // ---------- APIs ----------
    single {
        MovieApi(get())
    }

    single {
        AnimeApi(get())
    }

    // ---------- Catalog Repository ----------
    single<ContentRepository> {
        ContentRepositoryImpl(
            movieApi = get(),
            animeApi = get()
        )
    }

    // ---------- ViewModels ----------
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CatalogViewModel)
}
