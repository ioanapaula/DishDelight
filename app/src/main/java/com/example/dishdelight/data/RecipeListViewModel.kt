package com.example.dishdelight.data

import RecipeRepository
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecipeListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RecipeRepository = RecipeRepository()
    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> = _recipes

    init {
        fetchRecipes()
    }

    private fun fetchRecipes() {
        viewModelScope.launch {
            try {
                val recipes = repository.getRecipes()
                _recipes.value = recipes
            } catch (e: Exception) {
                Log.e("RecipeListTag", e.message, e)
            }
        }
    }
}

