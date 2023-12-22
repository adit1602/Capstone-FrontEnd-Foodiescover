package com.dicoding.foodiescover.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.foodiescover.models.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMeal(meal : Meal)

    @Delete
    suspend fun removeMeal(meal : Meal)

    @Query("SELECT * FROM meals")
    fun getMeals() : LiveData<List<Meal>>
}