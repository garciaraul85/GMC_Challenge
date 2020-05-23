package com.example.gm_challenge.di

import android.app.Application
import com.example.gm_challenge.R
import com.example.gm_challenge.di.BaseApplication.DatasourceProperties.API_KEY_PROPERTY
import com.example.gm_challenge.di.BaseApplication.DatasourceProperties.BASE_URL_PROPERTY
import org.koin.android.ext.android.setProperty
import org.koin.android.ext.android.startKoin

class BaseApplication : Application() {

    private lateinit var BASE_URL: String
    private lateinit var API_KEY: String

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(viewModelModule, repositoryModule, networkModule))

        BASE_URL = this.getString(R.string.BASE_URL)
        API_KEY = this.getString(R.string.API_KEY)
        setProperty(BASE_URL_PROPERTY, BASE_URL)
        setProperty(API_KEY_PROPERTY, API_KEY)
    }

    object DatasourceProperties {
        const val BASE_URL_PROPERTY = "BASE_URL_PROPERTY"
        const val API_KEY_PROPERTY = "API_KEY_PROPERTY"
    }
}