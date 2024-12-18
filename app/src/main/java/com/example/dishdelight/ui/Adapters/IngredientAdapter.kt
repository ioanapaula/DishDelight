package com.example.dishdelight.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dishdelight.data.Ingredient
import com.example.dishdelight.databinding.CellIngredientItemBinding

class IngredientAdapter(private val ingredients: List<Ingredient>) :
    RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    class IngredientViewHolder(private val binding: CellIngredientItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.ingredientName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding = CellIngredientItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.textView.text = ingredient.measure + " " + ingredient.name
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }
}