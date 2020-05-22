package com.example.gm_challenge.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.gm_challenge.data.Item
import kotlinx.android.synthetic.main.item_row.view.*

class ItemViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    fun bindItem(itemDrawer: Item, position: Int, previousSelectedItem: Int) {
        //populate text
        itemView.definition.text = itemDrawer.title

        if (previousSelectedItem == position)
            itemView.setBackgroundColor(Color.parseColor("#ff99cc00"))
        else
            itemView.setBackgroundColor(Color.parseColor("#ffffff"))
    }
}