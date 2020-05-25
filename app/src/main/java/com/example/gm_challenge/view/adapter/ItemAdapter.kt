package com.example.gm_challenge.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.gm_challenge.R
import com.example.gm_challenge.model.data.item.Track

class ItemAdapter(var previousSelectedItem: Int = -1,
                  private val clickListener: (Int, Track) -> Unit) :
    androidx.recyclerview.widget.RecyclerView.Adapter<ItemViewHolder>() {
    private var items: MutableList<Track> = mutableListOf()

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(items[position], position, previousSelectedItem)

        holder.itemView.setOnClickListener {
            items[position].isPlaying = !items[position].isPlaying
            clickListener(position, items[position])
            previousSelectedItem = position
            notifyDataSetChanged()
        }
    }

    fun playNextSong(): Track {
        items[previousSelectedItem].isPlaying = false
        if (previousSelectedItem < items.size) {
            previousSelectedItem++
        }

        items[previousSelectedItem].isPlaying = true
        return items[previousSelectedItem]
    }

    fun playPreviousSong(): Track {
        items[previousSelectedItem].isPlaying = false
        if (previousSelectedItem > 0) {
            previousSelectedItem--
        }
        items[previousSelectedItem].isPlaying = true
        return items[previousSelectedItem]
    }

    fun update(items: MutableList<Track>) {
        this.items.clear()
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
            ItemViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_row,
                            parent, false))
}