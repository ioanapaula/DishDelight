package com.example.dishdelight.viewmodels

import com.example.dishdelight.network.RecipeRepository
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dishdelight.data.Category
import kotlinx.coroutines.launch

class CategoryListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RecipeRepository = RecipeRepository()
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    init {
        fetchCategories()
    }

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                val categories = repository.getCategories()
                _categories.value = categories
            } catch (e: Exception) {
                Log.e("CategoryListTag", e.message, e)
            }
        }
    }
}