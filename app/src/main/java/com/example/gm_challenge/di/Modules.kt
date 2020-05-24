package com.example.gm_challenge.di

import com.example.gm_challenge.model.repository.LastFMRepository
import com.example.gm_challenge.model.repository.Repository
import com.example.gm_challenge.viewmodel.ElementViewModel
import com.example.gm_challenge.viewmodel.ItemViewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    // ViewModel for Search View
    factory { ElementViewModel(get()) }
    factory { ItemViewModel(get()) }
}

val repositoryModule = module {
    factory { LastFMRepository(get()) as Repository }
}