package com.example.gm_challenge.di

import android.app.Application
import org.koin.android.ext.android.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(viewModelModule, repositoryModule, networkModule))
    }
}