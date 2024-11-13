package com.example.dishdelight.data

import android.renderscript.Type
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class RecipeDetailsResponse(
    val meals: List<RecipeDetails>
)

data class RecipeDetails(
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMeal") val title: String,
    @SerializedName("strCategory") val category: String,
    @SerializedName("strArea") val area: String,
    @SerializedName("strTags") val tags: String,
    @SerializedName("strInstructions") val instructions: String,
    @SerializedName("strMealThumb") val imageUrl: String,
    @SerializedName("strYoutube") val youtubeUrl: String,
    @SerializedName("strSource") val recipeSourceUrl: String,
    //val ingredients: List<Ingredient>
)

data class Ingredient(
    val name: String,
    val measure: String,
    //val imageUrl: String
)