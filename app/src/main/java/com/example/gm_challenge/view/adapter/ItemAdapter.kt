package com.example.gm_challenge.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.gm_challenge.R
import com.example.gm_challenge.model.data.item.Track

class ItemAdapter(var previousSelectedItem: Int = -1,
                  var currentTrack: Track?,
                  private val clickListener: (Int, Track) -> Unit) :
    androidx.recyclerview.widget.RecyclerView.Adapter<ItemViewHolder>() {
    private var items: MutableList<Track> = mutableListOf()

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (currentTrack != null && position == previousSelectedItem) {
            items[position] = currentTrack!!
            currentTrack = null
        }
        holder.bindItem(items[position], position, previousSelectedItem)


        holder.itemView.setOnClickListener {
            if (previousSelectedItem >= 0) {
                items[position].isPlaying = items[previousSelectedItem].isPlaying
                items[previousSelectedItem].isPlaying = !items[previousSelectedItem].isPlaying
            } else {
                items[position].isPlaying = true
            }

            clickListener(position, items[position])
            previousSelectedItem = position
            notifyDataSetChanged()
        }
    }

    fun getCurrentSong(): Track {
        return items[previousSelectedItem]
    }

    fun playNextSong(): Track {
        items[previousSelectedItem].isPlaying = false
        if (previousSelectedItem < items.size) {
            previousSelectedItem++
        }
        return items[previousSelectedItem]
    }

    fun playPreviousSong(): Track {
        items[previousSelectedItem].isPlaying = false
        if (previousSelectedItem > 0) {
            previousSelectedItem--
        }
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