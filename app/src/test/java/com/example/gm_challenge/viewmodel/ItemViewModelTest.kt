package com.example.gm_challenge.viewmodel

import androidx.lifecycle.Observer
import com.example.gm_challenge.BaseTest
import com.example.gm_challenge.model.data.element.Tag
import com.example.gm_challenge.model.data.item.Artist
import com.example.gm_challenge.model.data.item.Image
import com.example.gm_challenge.model.data.item.Streamable
import com.example.gm_challenge.model.data.item.Track
import com.example.gm_challenge.whenever
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import java.net.UnknownHostException

class ItemViewModelTest: BaseTest() {
    lateinit var viewModel: ItemViewModel

    @Mock
    lateinit var observerState: Observer<ItemViewModel.AppState>

    lateinit var tag: Tag

    @Before
    override fun before() {
        super.before()

        viewModel = ItemViewModel(repository)

        tag = Tag("Pop", 1, 1)
    }

    @Test
    fun `getItemsByTag_noError_getTrack`() {
        val images= mutableListOf(Image("", ""))

        val artist = Artist("Michael Jackson", "", "")
        val streamable = Streamable(1, 1)
        val track = Track("BillieJean",1, "", "",
            streamable, artist, images, false)

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
}