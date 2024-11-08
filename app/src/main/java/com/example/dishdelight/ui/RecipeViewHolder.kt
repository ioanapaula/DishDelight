package com.example.dishdelight.ui

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dishdelight.data.Recipe
import com.example.dishdelight.databinding.CellRecipeItemBinding

class RecipeViewHolder(
    private val binding: CellRecipeItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(recipe: Recipe) {
        binding.recipe = recipe
        binding.executePendingBindings()

        val imageView = binding.recipeImage
        imageView.load(recipe.imageUrl) {
            crossfade(true)
            //placeholder(R.drawable.placeholder_image)
            //error(R.drawable.error_image)
        }
    }
}