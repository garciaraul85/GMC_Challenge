package com.example.gm_challenge.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.gm_challenge.di.BaseApplication
import com.example.gm_challenge.di.networkModule
import com.example.gm_challenge.di.repositoryModule
import com.example.gm_challenge.di.viewModelModule
import com.example.gm_challenge.model.data.element.Tag
import com.example.gm_challenge.model.data.item.Artist
import com.example.gm_challenge.model.data.item.Image
import com.example.gm_challenge.model.data.item.Streamable
import com.example.gm_challenge.model.data.item.Track
import com.example.gm_challenge.model.repository.LastFMRepository
import com.example.gm_challenge.whenever
import io.reactivex.Single
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.standalone.StandAloneContext
import org.koin.standalone.setProperty
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.net.UnknownHostException

class ItemViewModelTest: KoinTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    lateinit var viewModel: ItemViewModel

    @Mock
    lateinit var repository: LastFMRepository

    @Mock
    lateinit var observerState: Observer<ItemViewModel.AppState>

    lateinit var tag: Tag

    private lateinit var BASE_URL: String
    private lateinit var API_KEY: String

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        StandAloneContext.startKoin(listOf(viewModelModule, repositoryModule, networkModule))

        BASE_URL = "https://mock.com"
        API_KEY = "api_key"
        setProperty(BaseApplication.DatasourceProperties.BASE_URL_PROPERTY, BASE_URL)
        setProperty(BaseApplication.DatasourceProperties.API_KEY_PROPERTY, API_KEY)

        viewModel = ItemViewModel(repository)

        tag = Tag("Pop", 1, 1)
    }

    @Test
    fun `getItemsByTag_noError_getTrack`() {
        val images= mutableListOf(Image("", ""))

        val artist = Artist("Michael Jackson", "", "")
        val streamable = Streamable(1, 1)
        val track = Track("BillieJean",1, "", "",
            streamable, artist, images)

        val response = mutableListOf(track)

        whenever(repository.getTopTracksByTag(tag))
            .thenReturn(Single.just(response))

        viewModel.itemLiveData.observeForever(observerState)

        viewModel.getItemByElements(tag)
        assertEquals(viewModel.itemLiveData.value, ItemViewModel.AppState.SUCCESS(response))
    }

    @Test
    fun `getElements_noData_noTracksRetrieved`() {
        val response:MutableList<Track> = mutableListOf()

        whenever(repository.getTopTracksByTag(tag))
            .thenReturn(Single.just(response))

        viewModel.itemLiveData.observeForever(observerState)

        viewModel.getItemByElements(tag)
        assertEquals(viewModel.itemLiveData.value, ItemViewModel.AppState.ERROR("No songs Retrieved"))
    }

    @Test
    fun `getItems_unknownHostException_unknownHostExceptionError`() {
        whenever(repository.getTopTracksByTag(tag))
            .thenReturn(Single.error(UnknownHostException("No Internet Connection")))

        viewModel.itemLiveData.observeForever(observerState)

        viewModel.getItemByElements(tag)
        assertEquals(viewModel.itemLiveData.value, ItemViewModel.AppState.ERROR("No Internet Connection"))
    }

    @Test
    fun `getItems_otherException_ExceptionError`() {
        whenever(repository.getTopTracksByTag(tag))
            .thenReturn(Single.error(Exception("Error")))

        viewModel.itemLiveData.observeForever(observerState)

        viewModel.getItemByElements(tag)
        assertEquals(viewModel.itemLiveData.value, ItemViewModel.AppState.ERROR("Error"))
    }

    @After
    fun tearDown() {
        StandAloneContext.stopKoin()
    }

}