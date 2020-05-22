package com.example.gm_challenge

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gm_challenge.adapter.ItemAdapter
import com.example.gm_challenge.data.Item
import kotlinx.android.synthetic.main.fragment_item.rv_drawer_list
import java.util.ArrayList


class ItemFragment : Fragment() {
    private lateinit var adapter: ItemAdapter
    private var lastSelectedOption = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val titles = activity?.resources?.getStringArray(R.array.item_labels)
        val data = ArrayList<Item>()
        for (i in titles?.indices!!) {
            val navItem = Item(title = titles[i])
            data.add(navItem)
        }

        savedInstanceState?.let {
            lastSelectedOption = it.getInt("lastSelectedOption2", -1)
        }

        initRecycler(data)
    }

    private fun initRecycler(data: ArrayList<Item>) {
        adapter = ItemAdapter(lastSelectedOption) { word: Int -> partItemClicked(word) }
        adapter.update(data)
        rv_drawer_list.adapter = adapter
        rv_drawer_list.layoutManager = LinearLayoutManager(activity)
    }

    private fun partItemClicked(word: Int) {
        lastSelectedOption = word
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("lastSelectedOption2", lastSelectedOption)
    }

}