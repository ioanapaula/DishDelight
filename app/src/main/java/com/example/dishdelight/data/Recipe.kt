package com.example.dishdelight.data

import com.google.gson.annotations.SerializedName

data class RecipesResponse(
    val meals: List<Recipe>
)

data class Recipe(
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMeal") val title: String,
    @SerializedName("strMealThumb") val imageUrl: String,
)