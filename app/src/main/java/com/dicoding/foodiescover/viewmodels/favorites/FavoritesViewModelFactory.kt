package com.dicoding.foodiescover.viewmodels.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.foodiescover.repo.MealRepository

class FavoritesViewModelFactory(
    private val repository : MealRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoritesViewModel(repository) as T
    }
}