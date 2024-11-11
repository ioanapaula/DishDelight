package com.example.dishdelight.data

data class RecipeDetails(
    val id: String,
    val title: String,
    val category: String,
    val area: String,
    val tags: String,
    val instructions: String,
    val imageUrl: String,
    val youtubeUrl: String,
    val recipeSourceUrl: String,
    val ingredients: List<Ingredient>
)

data class Ingredient(
    val name: String,
    val measure: String,
    //val imageUrl: String
)