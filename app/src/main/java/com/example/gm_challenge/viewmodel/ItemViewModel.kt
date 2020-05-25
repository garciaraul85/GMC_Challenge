package com.example.gm_challenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gm_challenge.model.data.element.Tag
import com.example.gm_challenge.model.data.item.Track
import com.example.gm_challenge.model.repository.Repository
import com.example.gm_challenge.util.resources.EspressoIdlingResource
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException

class ItemViewModel(private val lastFMRepository: Repository): ViewModel() {
    private val disposable = CompositeDisposable()

    private val itemMutableLiveData = MutableLiveData<AppState>()
    val itemLiveData: LiveData<AppState>
        get() = itemMutableLiveData

    fun getItemByElements(tag: Tag) {
        EspressoIdlingResource.increment() // stops Espresso tests from going forward
        itemMutableLiveData.value = AppState.LOADING
        disposable.add(
            lastFMRepository.getTopTracksByTag(tag)
                .subscribe({
                    if (it.isEmpty()) {
                        itemMutableLiveData.value = AppState.ERROR("No songs Retrieved")
                    } else {
                        itemMutableLiveData.value = AppState.SUCCESS(it)
                    }
                    EspressoIdlingResource.decrement() // Tells Espresso test to resume
                }, {
                    //errors
                    val errorString = when (it) {
                        is UnknownHostException -> "No Internet Connection"
                        else -> it.localizedMessage
                    }
                    itemMutableLiveData.value = AppState.ERROR(errorString)
                    EspressoIdlingResource.decrement() // Tells Espresso test to resume
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    sealed class AppState {
        object LOADING : AppState()
        data class SUCCESS(val itemsList: MutableList<Track>) : AppState()
        data class ERROR(val message: String) : AppState()
    }
}