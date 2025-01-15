package com.example.dishdelight.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dishdelight.utils.Constants.RECIPE_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteRecipeDao {
    @Insert
    suspend fun insert(recipe: FavouriteRecipe)

    @Update
    suspend fun update(recipe: FavouriteRecipe)

    @Delete
    suspend fun delete(recipe: FavouriteRecipe)

    @Query("SELECT * FROM $RECIPE_TABLE_NAME")
    fun getAllRecipes(): MutableList<FavouriteRecipe>
}