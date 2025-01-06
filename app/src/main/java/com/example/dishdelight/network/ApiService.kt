package com.example.dishdelight.network

import com.example.dishdelight.data.AreasResponse
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

    @GET("list.php")
    suspend fun getAreaNames(@Query("a") list: String = "list"): AreasResponse

    @GET("filter.php")
    suspend fun getRecipes(
        @Query("c") category: String? = null,
        @Query("a") area: String? = null
    ): RecipesResponse

    @GET("lookup.php")
    suspend fun getRecipesDetails(@Query("i") id: String): RecipeDetailsResponse

    @GET("random.php")
    suspend fun getRandomRecipe(): RecipeDetailsResponse
}