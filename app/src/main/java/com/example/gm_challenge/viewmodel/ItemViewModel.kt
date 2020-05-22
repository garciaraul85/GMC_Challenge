package com.example.gm_challenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gm_challenge.data.Element
import com.example.gm_challenge.data.Item

class ItemViewModel: ViewModel() {
    private val itemMutableLiveData = MutableLiveData<MutableList<Item>>()
    val itemLiveData: LiveData<MutableList<Item>>
        get() = itemMutableLiveData

    fun getItemByElements(element: Element?) {
        val elements = mutableListOf<Item>()
        var filteredElements = mutableListOf<Item>()
        element.let {
            for (i in 0.. 10) {
                elements.add(Item("Title $i"))
            }
        }

        elements.forEach {
            if (it.elementId == element?.elementId) {
                filteredElements.add(it)
            }
        }

        itemMutableLiveData.value = filteredElements
    }

}