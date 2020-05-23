package com.example.gm_challenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gm_challenge.model.data.element.Tag
import com.example.gm_challenge.model.repository.LastFMRepository
import io.reactivex.disposables.CompositeDisposable

class ElementViewModel(private val lastFMRepository: LastFMRepository): ViewModel() {
    private val disposable = CompositeDisposable()
    private val elementMutableLiveData = MutableLiveData<MutableList<Tag>>()
    val elementLiveData: LiveData<MutableList<Tag>>
        get() = elementMutableLiveData

    fun getElements() {
        disposable.add(
            lastFMRepository.getTopTags()
                .subscribe({
                    elementMutableLiveData.value = it
                }, {})
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}