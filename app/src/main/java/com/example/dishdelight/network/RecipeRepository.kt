package com.example.dishdelight.network
import android.util.Log
import com.example.dishdelight.data.Category
import com.example.dishdelight.data.Recipe
import com.example.dishdelight.data.RecipeDetails

class RecipeRepository {
    private val recipesDetailsById = "https://www.themealdb.com/api/json/v1/1/lookup.php?i="

    suspend fun getCategories(): List<Category>{
        return try {
            val response = ApiClient.apiService.getCategories()
            response.categories
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching categories: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun getRecipes(recipeCategory: String): List<Recipe> {
        return try {
            val response = ApiClient.apiService.getRecipesByCategory(recipeCategory)
            response.meals
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching recipes by category: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun getRecipeDetails(recipeId: String): RecipeDetails {
        return try {
            val response = ApiClient.apiService.getRecipesDetails(recipeId)
            response.meals.first()
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching recipe details: ${e.message}", e)
            // temporary handling of error scenario
            RecipeDetails("", "", "", "", "", "", "", "", "", emptyList())
        }
    }
}
