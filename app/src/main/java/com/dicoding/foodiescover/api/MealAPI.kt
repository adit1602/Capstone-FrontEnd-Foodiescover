package com.dicoding.foodiescover.api

import com.dicoding.foodiescover.models.MealCategoryResponse
import com.dicoding.foodiescover.models.MealResponse
import com.dicoding.foodiescover.models.PopularMealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealAPI {

    //get random meal
    @GET("random.php")
    fun getRandomMeal() : Call<MealResponse>

    @GET("lookup.php?")
    fun getMealData(
        @Query("i")
        id : String
    ) : Call<MealResponse>

    @GET("filter.php?")
    fun getPopularMealByCategory(
        @Query("c")
        category : String
    ) : Call<PopularMealResponse>

    @GET("categories.php")
    fun getMealCategories() : Call<MealCategoryResponse>

    @GET("filter.php?")
    fun getMealsByCategory(
        @Query("c")
        category : String
    ) : Call<MealResponse>

    @GET("search.php?")
    fun getMealByFirstLetter(
        @Query("f")
        letter : Char
    ) : Call<MealResponse>

}