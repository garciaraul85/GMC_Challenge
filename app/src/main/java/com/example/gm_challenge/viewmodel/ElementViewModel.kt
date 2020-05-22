package com.example.gm_challenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gm_challenge.data.Element

class ElementViewModel: ViewModel() {
    private val elementMutableLiveData = MutableLiveData<MutableList<Element>>()
    val elementLiveData: LiveData<MutableList<Element>>
        get() = elementMutableLiveData

    fun getElements() {
        val elements = mutableListOf<Element>()
        for (i in 0.. 10) {
            elements.add(Element("Title $i"))
        }
        elementMutableLiveData.value = elements
    }

}