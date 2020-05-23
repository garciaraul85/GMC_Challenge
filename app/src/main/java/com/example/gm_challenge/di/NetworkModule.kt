package com.example.gm_challenge.di

import com.example.gm_challenge.di.DatasourceProperties.SERVER_URL
import com.example.gm_challenge.model.remote.LastFMRestService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    factory { provideOkHttpClient() }
    factory { provideApi(get()) }
    single { provideRetrofit(get(), SERVER_URL) }
}

object DatasourceProperties {
    const val SERVER_URL = "http://ws.audioscrobbler.com/2.0/"
}

fun provideApi(retrofit: Retrofit): LastFMRestService = retrofit.create(LastFMRestService::class.java)

fun provideOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .connectTimeout(15L, TimeUnit.SECONDS)
        .readTimeout(1L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}