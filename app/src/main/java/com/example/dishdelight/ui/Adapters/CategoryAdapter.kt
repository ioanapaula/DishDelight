package com.example.dishdelight.ui.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dishdelight.data.Category
import com.example.dishdelight.databinding.CellCategoryItemBinding
import com.example.dishdelight.ui.ViewHolders.CategoryViewHolder

class CategoryAdapter(
    private val onCategoryClick: (Category) -> Unit)
    : RecyclerView.Adapter<CategoryViewHolder>() {

    private var categoryList: List<Category> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CellCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding, onCategoryClick)
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
