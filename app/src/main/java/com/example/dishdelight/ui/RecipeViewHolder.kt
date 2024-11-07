package com.example.dishdelight.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.dishdelight.data.Recipe
import com.example.dishdelight.databinding.CellRecipeItemBinding

class RecipeViewHolder(
    private val binding: CellRecipeItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(recipe: Recipe) {
        binding.recipe = recipe
        binding.executePendingBindings()
    }
}