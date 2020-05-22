package com.example.gm_challenge.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.gm_challenge.R
import com.example.gm_challenge.data.Item

class ItemAdapter(private var previousSelectedItem: Int = -1,
                  private val clickListener: (Int) -> Unit) : RecyclerView.Adapter<ItemViewHolder>() {
    private var words: MutableList<Item> = mutableListOf()

    override fun getItemCount() = words.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(words[position], position, previousSelectedItem)

        holder.itemView.setOnClickListener {
            clickListener(position)
            previousSelectedItem = position
            notifyDataSetChanged()
        }
    }

    fun update(items: MutableList<Item>) {
        this.words.clear()
        this.words = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
            ItemViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_row,
                            parent, false))
}