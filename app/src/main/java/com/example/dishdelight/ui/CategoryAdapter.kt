package com.example.dishdelight.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dishdelight.data.Category
import com.example.dishdelight.data.Recipe
import com.example.dishdelight.databinding.CellCategoryItemBinding
import com.example.dishdelight.databinding.CellRecipeItemBinding

class CategoryAdapter : RecyclerView.Adapter<CategoryViewHolder>() {
    private var categoryList: List<Category> = emptyList() // Replace Recipe with your dataclass

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CellCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val recipe = categoryList[position]
        holder.bind(recipe)
    }

    fun submitList(categories: List<Category>) {
        categoryList = categories
        notifyDataSetChanged()
    }
}
