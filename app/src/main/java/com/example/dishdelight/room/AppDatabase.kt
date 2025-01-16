package com.example.dishdelight.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dishdelight.utils.Constants.RECIPE_DATABASE_NAME

@Database(entities = [FavouriteRecipe::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favouriteRecipeDao(): FavouriteRecipeDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val temporaryInstance = INSTANCE
            if(temporaryInstance != null){
                return temporaryInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    RECIPE_DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}