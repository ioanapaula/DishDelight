package com.example.dishdelight.network
import android.util.Log
import com.example.dishdelight.data.Category
import com.example.dishdelight.data.CategoryName
import com.example.dishdelight.data.Recipe
import com.example.dishdelight.data.RecipeDetails

class RecipeRepository {
    suspend fun getCategories(): List<Category>{
        return try {
            val response = ApiClient.apiService.getCategories()
            response.categories
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching categories: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun getCategoryNames(): List<CategoryName>{
        return try {
            val response = ApiClient.apiService.getCategoryNames()
            response.meals
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching category names: ${e.message}", e)
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

    suspend fun getRandomRecipe(): RecipeDetails {
        return try {
            val response = ApiClient.apiService.getRandomRecipe()
            response.meals.first()
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching random recipe details: ${e.message}", e)
            // temporary handling of error scenario
            RecipeDetails("", "", "", "", "", "", "", "", "", emptyList())
        }
    }
}
