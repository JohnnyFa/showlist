package com.fagundes.myshowlist.core.di

import com.fagundes.myshowlist.BuildConfig
import com.fagundes.myshowlist.core.data.remote.api.AnimeApi
import com.fagundes.myshowlist.core.data.remote.api.MovieApi
import com.fagundes.myshowlist.core.network.provideJikanHttpClient
import com.fagundes.myshowlist.core.network.provideTmdbHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val TMDB_CLIENT = "tmdb_client"
private const val JIKAN_CLIENT = "jikan_client"

val networkModule = module {
    single(named(TMDB_CLIENT)) {
        provideTmdbHttpClient(
            environment = get(),
            tmdbApiKey = BuildConfig.TMDB_API_KEY
        )
    }

    single(named(JIKAN_CLIENT)) {
        provideJikanHttpClient(environment = get())
    }

    single { MovieApi(get(named(TMDB_CLIENT))) }
    single { AnimeApi(get(named(JIKAN_CLIENT))) }
}
