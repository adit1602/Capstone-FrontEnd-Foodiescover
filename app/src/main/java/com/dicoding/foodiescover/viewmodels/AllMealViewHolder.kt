package com.dicoding.foodiescover.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.foodiescover.models.Meal
import com.dicoding.foodiescover.models.MealResponse
import com.dicoding.foodiescover.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllMealViewHolder : ViewModel() {

    private var filteredMealListLiveData = MutableLiveData<List<Meal>>()
    private val TAG = "AllMealViewHolder"

    fun getMealByFirstLetter(char : Char){
        RetrofitInstance.api.getMealByFirstLetter(char).enqueue(object : Callback<MealResponse>{
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {

                response.body()?.let { mealList ->
                    filteredMealListLiveData.postValue(mealList.meals)
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Log.e(TAG, "An error occurred ${t.message}")
            }

        })
    }

    fun observeFilteredMealListLiveData() : LiveData<List<Meal>> {
        return filteredMealListLiveData
    }
}