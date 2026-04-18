package com.fagundes.myshowlist.core.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val IO_DISPATCHER = "io_dispatcher"

val coroutineModule = module {
    single<CoroutineDispatcher>(named(IO_DISPATCHER)) { Dispatchers.IO }
}
