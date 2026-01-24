package com.fagundes.myshowlist.core.di

import com.fagundes.myshowlist.feat.home.vm.HomeViewModel
import com.fagundes.myshowlist.feat.login.data.FirebaseAuthRepository
import com.fagundes.myshowlist.feat.login.domain.AuthRepository
import com.fagundes.myshowlist.feat.login.domain.LoginWithGoogleUseCase
import com.fagundes.myshowlist.feat.login.vm.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val appModule = module {

    // Firebase
    single {
        FirebaseAuth.getInstance()
    }

    // Repository
    single<AuthRepository> {
        FirebaseAuthRepository(get())
    }

    // UseCase
    factory {
        LoginWithGoogleUseCase(get())
    }

    // ViewModel
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)

}
