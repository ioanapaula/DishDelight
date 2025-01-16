package com.example.dishdelight.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dishdelight.utils.Constants.RECIPE_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteRecipeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recipe: FavouriteRecipe)

    @Update
    fun update(recipe: FavouriteRecipe)

    @Delete
    fun delete(recipe: FavouriteRecipe)

    @Query("SELECT * FROM $RECIPE_TABLE_NAME")
    fun getAllRecipes(): MutableList<FavouriteRecipe>

    @Query("SELECT * FROM $RECIPE_TABLE_NAME WHERE recipeId LIKE :id")
    fun getRecipeById(id: Int): FavouriteRecipe

    @Query("SELECT EXISTS(SELECT 1 FROM $RECIPE_TABLE_NAME WHERE recipeId = :id)")
    fun isRecipeInFavourites(id: Int): Boolean
}