package com.example.dishdelight.network

import com.example.dishdelight.data.CategoriesResponse
import com.example.dishdelight.data.CategoryNamesResponse
import com.example.dishdelight.data.RecipeDetailsResponse
import com.example.dishdelight.data.RecipesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse

    @GET("list.php")
    suspend fun getCategoryNames(@Query("c") list: String = "list"): CategoryNamesResponse

    @GET("filter.php")
    suspend fun getRecipesByCategory(@Query("c") category: String): RecipesResponse

    @GET("lookup.php")
    suspend fun getRecipesDetails(@Query("i") id: String): RecipeDetailsResponse

    @GET("random.php")
    suspend fun getRandomRecipe(): RecipeDetailsResponse
}