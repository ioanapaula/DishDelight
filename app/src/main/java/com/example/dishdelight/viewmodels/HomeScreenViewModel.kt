package com.example.dishdelight.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dishdelight.data.RecipeDetails
import com.example.dishdelight.network.RecipeRepository
import kotlinx.coroutines.launch

class HomeScreenViewModel (application: Application) : AndroidViewModel(application)  {
    private val repository: RecipeRepository = RecipeRepository()
    private val _recipeDetails = MutableLiveData<RecipeDetails>()

    val recipeDetails: LiveData<RecipeDetails> = _recipeDetails

    fun fetchRandomRecipe(){
        viewModelScope.launch {
            try {
                val recipeDetails = repository.getRandomRecipe()
                _recipeDetails.value = recipeDetails
            }
            catch (e: Exception){
                Log.e("HomeRandomRecipeTag", e.message, e)
            }
        }
    }
}