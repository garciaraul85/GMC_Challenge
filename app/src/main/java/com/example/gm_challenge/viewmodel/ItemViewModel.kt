package com.example.gm_challenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gm_challenge.model.data.element.Tag
import com.example.gm_challenge.model.data.item.Track
import com.example.gm_challenge.model.repository.LastFMRepository
import io.reactivex.disposables.CompositeDisposable

class ItemViewModel(private val lastFMRepository: LastFMRepository): ViewModel() {
    private val disposable = CompositeDisposable()
    private val itemMutableLiveData = MutableLiveData<MutableList<Track>>()
    val itemLiveData: LiveData<MutableList<Track>>
        get() = itemMutableLiveData

    fun getItemByElements(tag: Tag?) {
        tag?.let {
            disposable.add(
                lastFMRepository.getTopTracksByTag(tag)
                    .subscribe({
                        itemMutableLiveData.value = it
                    }, {})
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}