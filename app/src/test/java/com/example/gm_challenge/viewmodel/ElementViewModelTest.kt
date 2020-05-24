package com.example.gm_challenge.viewmodel

import androidx.lifecycle.Observer
import com.example.gm_challenge.BaseTest
import com.example.gm_challenge.model.data.element.Tag
import com.example.gm_challenge.whenever
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import java.net.UnknownHostException

open class ElementViewModelTest: BaseTest() {

    lateinit var viewModel: ElementViewModel

    @Mock
    lateinit var observerState:Observer<ElementViewModel.AppState>

    @Before
    override fun before() {
        super.before()

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

}