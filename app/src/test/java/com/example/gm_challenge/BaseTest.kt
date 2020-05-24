package com.example.gm_challenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.gm_challenge.di.BaseApplication
import com.example.gm_challenge.di.networkModule
import com.example.gm_challenge.di.repositoryModule
import com.example.gm_challenge.di.viewModelModule
import com.example.gm_challenge.model.repository.LastFMRepository
import com.example.gm_challenge.viewmodel.ElementViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.standalone.StandAloneContext
import org.koin.standalone.setProperty
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.MockitoAnnotations

abstract class BaseTest: KoinTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: LastFMRepository

    protected lateinit var BASE_URL: String
    protected lateinit var API_KEY: String

    @Before
    open fun before() {
        MockitoAnnotations.initMocks(this)
        StandAloneContext.startKoin(listOf(viewModelModule, repositoryModule, networkModule))

        BASE_URL = "https://mock.com"
        API_KEY = "api_key"
        setProperty(BaseApplication.DatasourceProperties.BASE_URL_PROPERTY, BASE_URL)
        setProperty(BaseApplication.DatasourceProperties.API_KEY_PROPERTY, API_KEY)
    }

    @After
    fun tearDown() {
        StandAloneContext.stopKoin()
    }
}