package com.example.gm_challenge.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.gm_challenge.data.Element
import kotlinx.android.synthetic.main.element_row.view.*

class ElementViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    fun bindItem(itemDrawer: Element, position: Int, previousSelectedItem: Int) {
        //populate text
        itemView.definition.text = itemDrawer.title

        if (previousSelectedItem == position)
            itemView.setBackgroundColor(Color.parseColor("#ff99cc00"))
        else
            itemView.setBackgroundColor(Color.parseColor("#ffffff"))
    }
}