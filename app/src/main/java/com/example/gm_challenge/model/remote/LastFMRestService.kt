package com.example.gm_challenge.model.remote

import com.example.gm_challenge.model.data.element.Element
import com.example.gm_challenge.model.data.item.Item
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException
import java.util.concurrent.TimeUnit

interface LastFMRestService {
    @GET("?method=tag.getTopTags")
    fun getTopTag(): Single<Element>

    @GET("?method=tag.gettoptracks")
    fun getTopTracksByTag(@Query("tag") tag: String): Single<Item>
}