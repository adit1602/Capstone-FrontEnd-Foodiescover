package com.dicoding.foodiescover.viewmodels.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.foodiescover.models.Meal
import com.dicoding.foodiescover.repo.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(
    val repository : MealRepository
) : ViewModel() {

    val meals = repository.allMeal

    fun addMeal(meal : Meal) = viewModelScope.launch(Dispatchers.IO) {
        repository.addMeal(meal)
    }

    fun removeMeal(meal : Meal) = viewModelScope.launch(Dispatchers.IO) {
        repository.removeMeal(meal)
    }







}