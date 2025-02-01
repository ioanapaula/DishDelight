package com.example.dishdelight.viewmodels

import com.example.dishdelight.network.RecipeRepository
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dishdelight.data.RecipeDetails
import com.example.dishdelight.room.AppDatabase
import com.example.dishdelight.room.FavouriteRecipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(application: Application) : AndroidViewModel(application)  {
    private val database = AppDatabase.getDatabase(application)
    private val favouriteRecipeDao = database.favouriteRecipeDao()
    private val repository: RecipeRepository = RecipeRepository()

    private val _recipeDetails = MutableLiveData<RecipeDetails>()
    private val _hasSourceUrl = MutableLiveData<Boolean>()
    private val _hasYoutubeUrl = MutableLiveData<Boolean>()
    private val _isSavedToFavourites = MutableLiveData<Boolean>()

    val recipeDetails: LiveData<RecipeDetails> = _recipeDetails
    val hasSourceUrl: LiveData<Boolean> = _hasSourceUrl
    val hasYoutubeUrl: LiveData<Boolean> = _hasYoutubeUrl
    val isSavedToFavourites: LiveData<Boolean> = _isSavedToFavourites

    fun addToFavourites(recipeDetails: RecipeDetails) {
        viewModelScope.launch() {
            val favouriteRecipe = FavouriteRecipe(
                recipeId = recipeDetails.id.toIntOrNull() ?: 0,
                recipeTitle = recipeDetails.title,
                recipeCategory = recipeDetails.category,
                recipeArea = recipeDetails.area,
                recipeTags = recipeDetails.tags ?: "",
                recipeNotes = "",
                recipeImageUrl = recipeDetails.imageUrl + "/preview"
            )
            favouriteRecipeDao.insert(favouriteRecipe)
        }
    }

    fun isSavedToFavourites(recipeId: Int) {
        viewModelScope.launch{
            val isSaved = favouriteRecipeDao.isRecipeInFavourites(recipeId)
            _isSavedToFavourites.value = isSaved
        }
    }

    fun fetchRecipeDetails(recipeId: String){
        viewModelScope.launch {
            try {
                val recipeDetails = repository.getRecipeDetails(recipeId)
                _recipeDetails.value = recipeDetails
                _hasSourceUrl.value = !recipeDetails.recipeSourceUrl.isNullOrEmpty()
                _hasYoutubeUrl.value = !recipeDetails.youtubeUrl.isNullOrEmpty()
            }
            catch (e: Exception){
                Log.e("RecipeDetailsTag", e.message, e)
            }
        }
    }
}