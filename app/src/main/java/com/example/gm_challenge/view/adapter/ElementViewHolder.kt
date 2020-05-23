package com.example.gm_challenge.view.adapter

import android.graphics.Color
import android.view.View
import com.example.gm_challenge.model.data.element.Element
import com.example.gm_challenge.model.data.element.Tag
import kotlinx.android.synthetic.main.element_row.view.*

class ElementViewHolder(item: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(item) {
    fun bindItem(itemDrawer: Tag, position: Int, previousSelectedItem: Int) {
        //populate text
        itemView.definition.text = itemDrawer.name

        if (previousSelectedItem == position)
            itemView.setBackgroundColor(Color.parseColor("#ff99cc00"))
        else
            itemView.setBackgroundColor(Color.parseColor("#ffffff"))
    }
}