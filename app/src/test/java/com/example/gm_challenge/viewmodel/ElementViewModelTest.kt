package com.example.gm_challenge.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.gm_challenge.di.BaseApplication
import com.example.gm_challenge.di.networkModule
import com.example.gm_challenge.di.repositoryModule
import com.example.gm_challenge.di.viewModelModule
import com.example.gm_challenge.model.data.element.Tag
import com.example.gm_challenge.model.repository.LastFMRepository
import com.example.gm_challenge.whenever
import io.reactivex.Single
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.setProperty
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.net.UnknownHostException

class ElementViewModelTest: KoinTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    lateinit var viewModel: ElementViewModel

    @Mock
    lateinit var repository: LastFMRepository

    @Mock
    lateinit var observerState:Observer<ElementViewModel.AppState>

    private lateinit var BASE_URL: String
    private lateinit var API_KEY: String

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        startKoin(listOf(viewModelModule, repositoryModule, networkModule))

        BASE_URL = "https://mock.com"
        API_KEY = "api_key"
        setProperty(BaseApplication.DatasourceProperties.BASE_URL_PROPERTY, BASE_URL)
        setProperty(BaseApplication.DatasourceProperties.API_KEY_PROPERTY, API_KEY)

        viewModel = ElementViewModel(repository)
    }

    @Test
    fun `getElements_noError_getTags`() {
        val response = mutableListOf(Tag("term",1, 1))
        whenever(repository.getTopTags())
            .thenReturn(Single.just(response))

        viewModel.elementLiveData.observeForever(observerState)

        viewModel.getElements()
        assertEquals(viewModel.elementLiveData.value, ElementViewModel.AppState.SUCCESS(response))
    }

    @Test
    fun `getElements_noData_noTracksRetrieved`() {
        val response:MutableList<Tag> = mutableListOf()
        whenever(repository.getTopTags())
            .thenReturn(Single.just(response))

        viewModel.elementLiveData.observeForever(observerState)

        viewModel.getElements()
        assertEquals(viewModel.elementLiveData.value, ElementViewModel.AppState.ERROR("No Tracks Retrieved"))
    }

    @Test
    fun `getElements_unknownHostException_unknownHostExceptionError`() {
        whenever(repository.getTopTags())
            .thenReturn(Single.error(UnknownHostException("No Internet Connection")))

        viewModel.elementLiveData.observeForever(observerState)

        viewModel.getElements()
        assertEquals(viewModel.elementLiveData.value, ElementViewModel.AppState.ERROR("No Internet Connection"))
    }

    @Test
    fun `getElements_otherException_ExceptionError`() {
        whenever(repository.getTopTags())
            .thenReturn(Single.error(Exception("Error")))

        viewModel.elementLiveData.observeForever(observerState)

        viewModel.getElements()
        assertEquals(viewModel.elementLiveData.value, ElementViewModel.AppState.ERROR("Error"))
    }

    @After
    fun tearDown() {
        stopKoin()
    }

}