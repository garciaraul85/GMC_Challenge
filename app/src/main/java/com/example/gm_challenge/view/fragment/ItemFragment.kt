package com.example.gm_challenge.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.gm_challenge.R
import com.example.gm_challenge.model.data.element.Tag
import com.example.gm_challenge.view.MainActivity.Companion.ELEMENT
import com.example.gm_challenge.view.adapter.ItemAdapter
import com.example.gm_challenge.viewmodel.ItemViewModel
import kotlinx.android.synthetic.main.fragment_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemFragment : androidx.fragment.app.Fragment() {
    private lateinit var adapter: ItemAdapter
    private var lastSelectedOption = -1

    private val viewModel: ItemViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tag = arguments?.getParcelable(ELEMENT) as Tag

        savedInstanceState?.let {
            lastSelectedOption = it.getInt(LAST_SELECTED_OPTION2, -1)
        }

        setupRecycler(tag)
    }

    private fun setupRecycler(tag: Tag) {
        adapter = ItemAdapter(lastSelectedOption) { item: Int -> partItemClicked(item) }

        viewModel.itemLiveData.observe(this, Observer {
            adapter.update(it)
        })

        rv_drawer_list.adapter = adapter
        rv_drawer_list.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(activity)

        viewModel.getItemByElements(tag)
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