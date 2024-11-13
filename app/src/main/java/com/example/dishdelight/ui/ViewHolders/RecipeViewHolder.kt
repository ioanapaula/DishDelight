package com.example.dishdelight.ui.ViewHolders

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dishdelight.R
import com.example.dishdelight.data.Recipe
import com.example.dishdelight.databinding.CellRecipeItemBinding

class RecipeViewHolder(
    private val binding: CellRecipeItemBinding,
    private val onRecipeClicked: (Recipe) -> Unit)
    : RecyclerView.ViewHolder(binding.root) {

        init {
        itemView.setOnClickListener {
            onRecipeClicked(binding.recipe!!)
        }
    }

    fun bind(recipe: Recipe) {
        binding.recipe = recipe
        binding.executePendingBindings()

        val imageView = binding.recipeImage
        imageView.load(recipe.imageUrl + "/preview") {
            crossfade(true)
            placeholder(R.drawable.ic_file_placeholder)
            error(R.drawable.ic_error)
        }
    }
}