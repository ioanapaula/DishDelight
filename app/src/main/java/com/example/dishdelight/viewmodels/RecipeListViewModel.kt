package com.example.dishdelight.viewmodels

import com.example.dishdelight.network.RecipeRepository
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dishdelight.data.Recipe
import kotlinx.coroutines.launch

class RecipeListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RecipeRepository = RecipeRepository()
    private val _recipes = MutableLiveData<List<Recipe>>()

    val recipes: LiveData<List<Recipe>> = _recipes

    fun fetchRecipes(category: String? = null, area: String? = null) {
        viewModelScope.launch {
            try {
                val recipes = repository.getRecipes(recipeCategory = category, recipeArea = area)
                _recipes.value = recipes
            } catch (e: Exception) {
                Log.e("RecipeListTag", e.message, e)
            }
        }
    }
}

