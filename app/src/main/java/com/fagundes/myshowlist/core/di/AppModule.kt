package com.fagundes.myshowlist.core.di

import androidx.room.Room
import com.fagundes.myshowlist.core.data.local.dao.ContentDao
import com.fagundes.myshowlist.core.data.local.enum.ContentType
import com.fagundes.myshowlist.core.db.AppDatabase
import com.fagundes.myshowlist.feat.catalog.data.repository.CatalogRepository
import com.fagundes.myshowlist.feat.catalog.data.repository.CatalogRepositoryImpl
import com.fagundes.myshowlist.feat.catalog.vm.CatalogViewModel
import com.fagundes.myshowlist.feat.detail.data.repository.DetailRepository
import com.fagundes.myshowlist.feat.detail.data.repository.DetailRepositoryImpl
import com.fagundes.myshowlist.feat.detail.vm.DetailViewModel
import com.fagundes.myshowlist.feat.home.data.local.datasource.HomeLocalDataSource
import com.fagundes.myshowlist.feat.home.data.local.datasource.HomeLocalDataSourceImpl
import com.fagundes.myshowlist.feat.home.data.remote.HomeRemoteDataSource
import com.fagundes.myshowlist.feat.home.data.remote.HomeRemoteDataSourceImpl
import com.fagundes.myshowlist.feat.home.data.repository.HomeRepository
import com.fagundes.myshowlist.feat.home.data.repository.HomeRepositoryImpl
import com.fagundes.myshowlist.feat.home.vm.HomeViewModel
import com.fagundes.myshowlist.feat.login.data.FirebaseAuthRepository
import com.fagundes.myshowlist.feat.login.domain.AuthRepository
import com.fagundes.myshowlist.feat.login.domain.LoginWithGoogleUseCase
import com.fagundes.myshowlist.feat.login.vm.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    // ---------- Firebase ----------
    single { FirebaseAuth.getInstance() }

    // ---------- Auth ----------
    single<AuthRepository> { FirebaseAuthRepository(get()) }
    factory { LoginWithGoogleUseCase(get()) }

    // ---------- Database ----------
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "myshowlist.db"
        ).build()
    }

    single<ContentDao> { get<AppDatabase>().contentDao() }

    // ---------- Remote DataSource ----------
    single<HomeRemoteDataSource> {
        HomeRemoteDataSourceImpl(movieApi = get())
    }

    // ---------- Local DataSource ----------
    single<HomeLocalDataSource> {
        HomeLocalDataSourceImpl(get())
    }

    // ---------- Repository ----------
    single<HomeRepository> {
        HomeRepositoryImpl(
            local = get(),
            remote = get(),
            ioDispatcher = get(named(IO_DISPATCHER))
        )
    }

    single<CatalogRepository> {
        CatalogRepositoryImpl(get())
    }

    single<DetailRepository> {
        DetailRepositoryImpl(get())
    }

    // ---------- ViewModels ----------
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CatalogViewModel)

    viewModel { (id: Int, type: ContentType) ->
        DetailViewModel(
            id = id,
            type = type,
            repository = get()
        )
    }
}
