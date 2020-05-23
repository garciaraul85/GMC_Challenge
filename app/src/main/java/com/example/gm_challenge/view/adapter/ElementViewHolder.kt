package com.example.gm_challenge.view.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import com.example.gm_challenge.model.data.element.Tag
import com.example.gm_challenge.util.coolFormat
import kotlinx.android.synthetic.main.element_row.view.*

class ElementViewHolder(item: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(item) {
    @SuppressLint("SetTextI18n")
    fun bindItem(itemDrawer: Tag, position: Int, previousSelectedItem: Int) {
        //populate text
        itemView.name.text = itemDrawer.name

        itemView.count.text = "count ${coolFormat(itemDrawer.count.toDouble(), 0)}"
        itemView.reach.text = "reach ${coolFormat(itemDrawer.reach.toDouble(), 0)}"

        if (previousSelectedItem == position)
            itemView.alpha = 0.5f
        else
            itemView.alpha = 1f
    }
}
