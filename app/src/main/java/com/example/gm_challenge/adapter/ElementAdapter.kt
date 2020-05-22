package com.example.gm_challenge.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.gm_challenge.R
import com.example.gm_challenge.data.Element

class ElementAdapter(private var previousSelectedItem: Int = -1,
                     private val clickListener: (Int) -> Unit) : RecyclerView.Adapter<ElementViewHolder>() {
    private var words: MutableList<Element> = mutableListOf()

    override fun getItemCount() = words.size

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        holder.bindItem(words[position], position, previousSelectedItem)

        holder.itemView.setOnClickListener {
            clickListener(position)
            previousSelectedItem = position
            notifyDataSetChanged()
        }
    }

    fun update(elements: MutableList<Element>) {
        this.words.clear()
        this.words = elements
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder =
            ElementViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.element_row,
                            parent, false))
}