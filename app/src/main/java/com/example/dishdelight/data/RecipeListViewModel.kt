package com.example.dishdelight.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class RecipeListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RecipeRepository = RecipeRepository()
    val recipes: List<Recipe> = repository.getRecipes()
    val selectedRecipe: MutableLiveData<Recipe> = MutableLiveData()

    fun selectRecipe(recipe: Recipe) {
        selectedRecipe.value = recipe
    }
}
