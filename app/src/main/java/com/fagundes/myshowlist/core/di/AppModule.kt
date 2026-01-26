package com.fagundes.myshowlist.core.di

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
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

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
        io.ktor.client.HttpClient(io.ktor.client.engine.okhttp.OkHttp) {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json(
                    kotlinx.serialization.json.Json {
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(io.ktor.client.plugins.logging.Logging) {
                level = io.ktor.client.plugins.logging.LogLevel.BODY
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
