package com.example.dishdelight.ui

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dishdelight.data.Category
import com.example.dishdelight.databinding.CellCategoryItemBinding

class CategoryViewHolder(
    private val binding: CellCategoryItemBinding,
    private val onCategoryClick: (Category) -> Unit)
    : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            onCategoryClick(binding.category!!)
        }
    }

    fun bind(category: Category) {
        binding.category = category
        binding.executePendingBindings()

        val imageView = binding.categoryImage
        imageView.load(category.imageUrl) {
            crossfade(true)
        }
    }
}