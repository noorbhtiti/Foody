package com.ltu.foody.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ltu.foody.model.Recipes


@Dao
interface RecipeDatabaseDao {

    @Insert
    suspend fun insert(recipes : Recipes)


    @Delete
    suspend fun delete(recipes: Recipes)


    @Query("SELECT * from recipes ORDER BY id ASC")
    suspend fun getAllRecipes():List<Recipes>


    @Query("SELECT EXISTS(SELECT * from recipes WHERE id = :id)")
    suspend fun isFavorite(id:Int):Boolean
}