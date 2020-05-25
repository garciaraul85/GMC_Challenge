package com.example.gm_challenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gm_challenge.model.data.element.Tag
import com.example.gm_challenge.model.repository.Repository
import com.example.gm_challenge.util.resources.EspressoIdlingResource
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException

class ElementViewModel(private val lastFMRepository: Repository): ViewModel() {
    private val disposable = CompositeDisposable()
    private val elementMutableLiveData = MutableLiveData<AppState>()
    val elementLiveData: LiveData<AppState>
        get() = elementMutableLiveData

    fun getElements() {
        EspressoIdlingResource.increment() // stops Espresso tests from going forward
        elementMutableLiveData.value = AppState.LOADING
        disposable.add(
            lastFMRepository.getTopTags()
                .subscribe({
                    if (it.isEmpty()) {
                        elementMutableLiveData.value = AppState.ERROR("No Tracks Retrieved")
                    } else {
                        elementMutableLiveData.value = AppState.SUCCESS(it)
                    }
                    EspressoIdlingResource.decrement() // Tells Espresso test to resume
                }, {
                    //errors
                    val errorString = when (it) {
                        is UnknownHostException -> "No Internet Connection"
                        else -> it.localizedMessage
                    }
                    elementMutableLiveData.value = AppState.ERROR(errorString)
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
        data class SUCCESS(val elementsList: MutableList<Tag>) : AppState()
        data class ERROR(val message: String) : AppState()
    }
}