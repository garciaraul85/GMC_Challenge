package com.example.gm_challenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gm_challenge.model.data.element.Tag
import com.example.gm_challenge.model.repository.Repository
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException

class ElementViewModel(private val lastFMRepository: Repository): ViewModel() {
    private val disposable = CompositeDisposable()
    private val elementMutableLiveData = MutableLiveData<AppState>()
    val elementLiveData: LiveData<AppState>
        get() = elementMutableLiveData

    fun getElements() {
        elementMutableLiveData.value = AppState.LOADING
        disposable.add(
            lastFMRepository.getTopTags()
                .subscribe({
                    if (it.isEmpty()) {
                        elementMutableLiveData.value = AppState.ERROR("No Tracks Retrieved")
                    } else {
                        elementMutableLiveData.value = AppState.SUCCESS(it)
                    }
                }, {
                    //errors
                    val errorString = when (it) {
                        is UnknownHostException -> "No Internet Connection"
                        else -> it.localizedMessage
                    }
                    elementMutableLiveData.value = AppState.ERROR(errorString)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    sealed class AppState {
        object LOADING : AppState()
        data class SUCCESS(val wordsList: MutableList<Tag>) : AppState()
        data class ERROR(val message: String) : AppState()
    }
}