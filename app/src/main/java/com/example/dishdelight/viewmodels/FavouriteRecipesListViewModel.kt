package com.example.dishdelight.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dishdelight.room.AppDatabase
import com.example.dishdelight.room.FavouriteRecipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteRecipesListViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val favouriteRecipeDao = database.favouriteRecipeDao()

    private val _recipes = MutableLiveData<List<FavouriteRecipe>>()

    val recipes: LiveData<List<FavouriteRecipe>> = _recipes

    fun fetchFavouriteRecipes(){
        viewModelScope.launch {
            val recipes = favouriteRecipeDao.getAllRecipes()
            _recipes.value = recipes
        }
    }
}