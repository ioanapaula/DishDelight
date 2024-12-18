package com.example.dishdelight.viewmodels

import com.example.dishdelight.network.RecipeRepository
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dishdelight.data.RecipeDetails
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(application: Application) : AndroidViewModel(application)  {
    private val repository: RecipeRepository = RecipeRepository()
    private val _recipeDetails = MutableLiveData<RecipeDetails>()

    val recipeDetails: LiveData<RecipeDetails> = _recipeDetails

    fun fetchRecipeDetails(recipeId: String){
        viewModelScope.launch {
            try {
                val recipeDetails = repository.getRecipeDetails(recipeId)
                _recipeDetails.value = recipeDetails
            }
            catch (e: Exception){
                Log.e("RecipeDetailsTag", e.message, e)
            }
        }
    }
}