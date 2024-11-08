package com.example.dishdelight.ui

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dishdelight.data.Category
import com.example.dishdelight.databinding.CellCategoryItemBinding

class CategoryViewHolder(
    private val binding: CellCategoryItemBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(category: Category) {
        binding.category = category
        binding.executePendingBindings()

        val imageView = binding.categoryImage
        imageView.load(category.imageUrl) {
            crossfade(true)
        }
    }
}