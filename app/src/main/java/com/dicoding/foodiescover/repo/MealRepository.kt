package com.dicoding.foodiescover.repo

import com.dicoding.foodiescover.db.MealDao
import com.dicoding.foodiescover.models.Meal

class MealRepository(
    private val mealDao : MealDao
) {
    val allMeal = mealDao.getMeals()

    suspend fun addMeal(meal : Meal){
        mealDao.addMeal(meal)
    }

    suspend fun removeMeal(meal : Meal){
        mealDao.removeMeal(meal)
    }

}