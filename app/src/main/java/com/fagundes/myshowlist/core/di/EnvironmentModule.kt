package com.fagundes.myshowlist.core.di

import com.fagundes.myshowlist.BuildConfig
import com.fagundes.myshowlist.core.config.AppEnvironment
import com.fagundes.myshowlist.core.config.provideEnvironment
import org.koin.dsl.module

val environmentModule = module {
    single<AppEnvironment> { provideEnvironment(BuildConfig.ENV) }
}
