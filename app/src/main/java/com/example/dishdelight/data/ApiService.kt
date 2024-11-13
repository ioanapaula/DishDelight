package com.example.dishdelight.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse

    @GET("filter.php")
    suspend fun getRecipesByCategory(@Query("c") category: String): RecipesResponse

    @GET("lookup.php")
    suspend fun getRecipesDetails(@Query("i") id: String): RecipeDetailsResponse
}