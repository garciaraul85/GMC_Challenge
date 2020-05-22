package com.example.gm_challenge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.gm_challenge.R
import com.example.gm_challenge.data.Element

class ElementAdapter(private var previousSelectedItem: Int = -1,
                     private val clickListener: (Int, Element) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<ElementViewHolder>() {
    private var elements: MutableList<Element> = mutableListOf()

    override fun getItemCount() = elements.size

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        holder.bindItem(elements[position], position, previousSelectedItem)

        holder.itemView.setOnClickListener {
            clickListener(position, elements[position])
            previousSelectedItem = position
            notifyDataSetChanged()
        }
    }

    fun update(elements: MutableList<Element>) {
        this.elements.clear()
        this.elements = elements
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder =
            ElementViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.element_row,
                            parent, false))
}