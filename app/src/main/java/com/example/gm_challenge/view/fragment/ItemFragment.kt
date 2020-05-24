package com.example.gm_challenge.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.gm_challenge.R
import com.example.gm_challenge.model.data.element.Tag
import com.example.gm_challenge.model.data.item.Track
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
        val tag = arguments?.getParcelable(ELEMENT) as Tag?

        savedInstanceState?.let {
            lastSelectedOption = it.getInt(LAST_SELECTED_OPTION2, -1)
        }

        setupRecycler(tag)
    }

    private fun setupRecycler(tag: Tag?) {
        adapter = ItemAdapter(lastSelectedOption) { item: Int -> partItemClicked(item) }
        viewModel.itemLiveData.observe(this, Observer { appState ->
            when (appState) {
                is ItemViewModel.AppState.LOADING -> displayLoading()
                is ItemViewModel.AppState.SUCCESS -> displayTracks(appState.itemsList)
                is ItemViewModel.AppState.ERROR -> displayMessage(appState.message)
                else -> displayMessage("Something Went Wrong... Try Again.")
            }
        })

        rv_drawer_list.adapter = adapter
        rv_drawer_list.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(activity)
        tag?.let { viewModel.getItemByElements(it) }

    }

    private fun displayTracks(tracksList: MutableList<Track>) {
        // set recycler to eliminate flicker
        adapter.update(tracksList)

        // set correct visible element
        progressBar.visibility = View.GONE
        rv_drawer_list.visibility = View.VISIBLE
        messageText.visibility = View.GONE
    }

    private fun displayLoading() {
        // set correct visible element
        progressBar.visibility = View.VISIBLE
        rv_drawer_list.visibility = View.GONE
        messageText.visibility = View.GONE
    }

    private fun displayMessage(message: String) {
        // set correct visible element
        progressBar.visibility = View.GONE
        rv_drawer_list.visibility = View.GONE
        messageText.visibility = View.VISIBLE
        //set message
        messageText.text = message
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