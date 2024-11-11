package com.example.dishdelight.ui.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dishdelight.data.Recipe
import com.example.dishdelight.databinding.CellRecipeItemBinding
import com.example.dishdelight.ui.ViewHolders.RecipeViewHolder

class RecipeAdapter(
    private val onRecipeClick: (Recipe) -> Unit)
    : RecyclerView.Adapter<RecipeViewHolder>() {

    private var recipeList: List<Recipe> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = CellRecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding, onRecipeClick)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeList[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    fun submitList(recipes: List<Recipe>) {
        recipeList = recipes
        notifyDataSetChanged()
    }
}