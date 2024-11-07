package com.example.dishdelight.data

class RecipeRepository {

    fun getRecipes(): List<Recipe>{
        // temporary hardcoded list of recipe
        val recipe1 = Recipe("Grilled cheese", "url1", "")
        val recipe2 = Recipe("Chicken saltimboca", "url1", "")
        val recipe3 = Recipe("Caprese salad", "url1", "")
        val recipe4 = Recipe("Caesar salad", "url1", "")
        val recipe5 = Recipe("Sushi", "url1", "")
        val recipe6 = Recipe("Ramen", "url1", "")
        val recipe7 = Recipe("Butter chicken", "url1", "")
        val recipe8 = Recipe("Egg fried rice", "url1", "")
        val recipe9 = Recipe("Mashed potatoes", "url1", "")
        val recipe10 = Recipe("Spaghetti carbonara", "url1", "")

        val recipes = listOf(recipe1, recipe2, recipe3, recipe4, recipe5, recipe6, recipe7, recipe8, recipe9, recipe10)

        // Implement API call using Retrofit or another networking library
        return recipes
    }
}
