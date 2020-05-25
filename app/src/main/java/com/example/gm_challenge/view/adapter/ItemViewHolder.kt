package com.example.gm_challenge.view.adapter

import android.annotation.SuppressLint
import android.view.View
import com.example.gm_challenge.R
import com.example.gm_challenge.model.data.item.Track
import com.example.gm_challenge.util.timeFormater
import kotlinx.android.synthetic.main.item_row.view.*

class ItemViewHolder(item: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(item) {
    @SuppressLint("ResourceAsColor")
    fun bindItem(itemDrawer: Track, position: Int, previousSelectedItem: Int) {
        //populate text
        itemView.song.text = itemDrawer.name
        itemView.artist.text = itemDrawer.artist.name
        itemView.duration.text = timeFormater(itemDrawer.duration.toDouble().toLong())

        if (previousSelectedItem == position) {
            itemView.preview_button.setBackgroundResource(R.drawable.pause_round)
            itemView.setBackgroundColor(R.color.FlatWhite)
            itemView.alpha = 0.7f
        } else {
            itemView.preview_button.setBackgroundResource(R.drawable.play_round)
            itemView.setBackgroundColor(R.color.white)
            itemView.alpha = 1f
        }
    }

}