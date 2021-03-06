package com.example.gm_challenge.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.gm_challenge.R
import com.example.gm_challenge.model.data.element.Tag
import com.example.gm_challenge.model.data.item.Track
import com.example.gm_challenge.service.Constants
import com.example.gm_challenge.service.MessageEvent
import com.example.gm_challenge.service.MessageEvent.Companion.NEXT
import com.example.gm_challenge.service.MessageEvent.Companion.PREVIOUS
import com.example.gm_challenge.service.PlayerService
import com.example.gm_challenge.view.MainActivity.Companion.ELEMENT
import com.example.gm_challenge.view.adapter.ItemAdapter
import com.example.gm_challenge.viewmodel.ItemViewModel
import kotlinx.android.synthetic.main.fragment_item.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemFragment : androidx.fragment.app.Fragment() {
    private lateinit var adapter: ItemAdapter
    private var lastSelectedOption = -1
    private var currentTrack: Track? = null

    private val viewModel: ItemViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tag = arguments?.getParcelable(ELEMENT) as Tag?

        EventBus.getDefault().register(this)

        savedInstanceState?.let {
            lastSelectedOption = it.getInt(LAST_SELECTED_OPTION2, -1)
            currentTrack = it.getParcelable(PLAY_TRACK)
        }

        setupRecycler(tag)
    }

    private fun setupRecycler(tag: Tag?) {
        adapter = ItemAdapter(lastSelectedOption, currentTrack) { item: Int, track: Track -> itemClicked(item, track) }
        viewModel.itemLiveData.observe(this, Observer { appState ->
            when (appState) {
                is ItemViewModel.AppState.LOADING -> displayLoading()
                is ItemViewModel.AppState.SUCCESS -> displayTracks(appState.itemsList)
                is ItemViewModel.AppState.ERROR -> displayMessage(appState.message)
                else -> displayMessage(getString(R.string.generic_error))
            }
        })

        rv_item_list.adapter = adapter
        rv_item_list.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(activity)
        tag?.let { viewModel.getItemByElements(it) }

    }

    private fun displayTracks(tracksList: MutableList<Track>) {
        // set recycler to eliminate flicker
        adapter.update(tracksList)

        // set correct visible element
        progressBar.visibility = View.GONE
        rv_item_list.visibility = View.VISIBLE
        messageText.visibility = View.GONE
    }

    private fun displayLoading() {
        // set correct visible element
        progressBar.visibility = View.VISIBLE
        rv_item_list.visibility = View.GONE
        messageText.visibility = View.GONE
    }

    private fun displayMessage(message: String) {
        // set correct visible element
        progressBar.visibility = View.GONE
        rv_item_list.visibility = View.GONE
        messageText.visibility = View.VISIBLE
        //set message
        messageText.text = message
    }

    private fun itemClicked(position: Int, track: Track) {
        lastSelectedOption = position
        currentTrack = track

        val intent = Intent(activity, PlayerService::class.java)
        intent.putExtra(PLAY_TRACK, track)

        if (!PlayerService.IS_SERVICE_RUNNING) {
            intent.action = Constants.ACTION.STARTFOREGROUND_ACTION
            PlayerService.IS_SERVICE_RUNNING = true
            activity?.startService(intent)
        }
        EventBus.getDefault().post(track)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.event) {
            NEXT -> {
                EventBus.getDefault().post(adapter.playNextSong())
                rv_item_list.findViewHolderForAdapterPosition(adapter.previousSelectedItem)?.itemView?.performClick()
            }
            PREVIOUS -> {
                EventBus.getDefault().post(adapter.playPreviousSong())
                rv_item_list.findViewHolderForAdapterPosition(adapter.previousSelectedItem)?.itemView?.performClick()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(LAST_SELECTED_OPTION2, lastSelectedOption)
        outState.putParcelable(PLAY_TRACK, adapter.getCurrentSong())
    }

    companion object {
        const val LAST_SELECTED_OPTION2 = "lastSelectedOption2"
        const val PLAY_TRACK = "playTrack"
    }
}