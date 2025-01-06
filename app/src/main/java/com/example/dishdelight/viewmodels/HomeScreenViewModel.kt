package com.example.dishdelight.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dishdelight.data.AreaName
import com.example.dishdelight.data.CategoryName
import com.example.dishdelight.data.RecipeDetails
import com.example.dishdelight.network.RecipeRepository
import kotlinx.coroutines.launch

class HomeScreenViewModel (application: Application) : AndroidViewModel(application)  {
    private val repository: RecipeRepository = RecipeRepository()
    private val _recipeDetails = MutableLiveData<RecipeDetails>()
    private val _categories = MutableLiveData<List<CategoryName>>()
    private val _areas = MutableLiveData<List<AreaName>>()

    val recipeDetails: LiveData<RecipeDetails> = _recipeDetails
    val categoryList: LiveData<List<CategoryName>> = _categories
    val areaList: LiveData<List<AreaName>> = _areas

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

    fun fetchCategoryList(){
        viewModelScope.launch {
            try {
                val categories = repository.getCategoryNames()
                _categories.value = categories
            }
            catch (e: Exception){
                Log.e("HomeCategoriesTag", e.message, e)
            }
        }
    }

    fun fetchAreaList(){
        viewModelScope.launch {
            try {
                val areas = repository.getAreaNames()
                _areas.value = areas
            }
            catch (e: Exception){
                Log.e("HomeAreasTag", e.message, e)
            }
        }
    }
}