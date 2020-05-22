package com.example.gm_challenge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.gm_challenge.R
import com.example.gm_challenge.data.Item

class ItemAdapter(private var previousSelectedItem: Int = -1,
                  private val clickListener: (Int) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<ItemViewHolder>() {
    private var items: MutableList<Item> = mutableListOf()

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(items[position], position, previousSelectedItem)

        holder.itemView.setOnClickListener {
            clickListener(position)
            previousSelectedItem = position
            notifyDataSetChanged()
        }
    }

    fun update(items: MutableList<Item>) {
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