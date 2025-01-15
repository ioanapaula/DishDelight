package com.example.dishdelight.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dishdelight.utils.Constants.RECIPE_TABLE_NAME

@Entity(tableName = RECIPE_TABLE_NAME)
data class FavouriteRecipe(
    @PrimaryKey val recipeId: Int,
    val recipeTitle: String,
    val recipeCategory: String,
    val recipeArea: String,
    val recipeTags: String,
    val recipeNotes: String,
    val recipeImageUrl: String
)