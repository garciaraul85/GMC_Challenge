package com.example.gm_challenge.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.gm_challenge.view.MainActivity.Companion.ELEMENT
import com.example.gm_challenge.R
import com.example.gm_challenge.adapter.ItemAdapter
import com.example.gm_challenge.data.Element
import com.example.gm_challenge.viewmodel.ItemViewModel
import com.example.gm_challenge.viewmodel.ItemViewModelFactory
import kotlinx.android.synthetic.main.fragment_item.*

class ItemFragment : androidx.fragment.app.Fragment() {
    private lateinit var adapter: ItemAdapter
    private var lastSelectedOption = -1

    lateinit var viewModel: ItemViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val element = arguments?.getParcelable(ELEMENT) as Element?

        setupViewModel()

        savedInstanceState?.let {
            lastSelectedOption = it.getInt(LAST_SELECTED_OPTION2, -1)
        }

        initRecycler(element)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ItemViewModelFactory()
        ).get(ItemViewModel::class.java)
    }

    private fun initRecycler(element: Element?) {
        adapter = ItemAdapter(lastSelectedOption) { item: Int -> partItemClicked(item) }

        viewModel.itemLiveData.observe(this, Observer {
            adapter.update(it)
        })

        rv_drawer_list.adapter = adapter
        rv_drawer_list.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(activity)

        viewModel.getItemByElements(element)
    }

    private fun partItemClicked(word: Int) {
        lastSelectedOption = word
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(LAST_SELECTED_OPTION2, lastSelectedOption)
    }

    companion object {
        const val LAST_SELECTED_OPTION2 = "lastSelectedOption2"
    }

}