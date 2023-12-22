package com.dicoding.foodiescover.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.foodiescover.adapters.CategoryMealsAdapter
import com.dicoding.foodiescover.databinding.ActivityCategoryMealsBinding
import com.dicoding.foodiescover.ui.fragments.HomeFragment
import com.dicoding.foodiescover.viewmodels.home.MealCategoryViewModel

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCategoryMealsBinding
    private lateinit var mealCategoryVM : MealCategoryViewModel
    private lateinit var categoryMealsAdapter : CategoryMealsAdapter
    private val TAG = "CategoryMealsActivity"
    private val MEAL_ID = "MEAL_ID"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        mealCategoryVM = ViewModelProvider(this)[MealCategoryViewModel::class.java]
        categoryMealsAdapter = CategoryMealsAdapter()

        setContentView(binding.root)


        setUpLiveData()
        setUpRecyclerView()



    }

    private fun setUpRecyclerView() {
        binding.rvCategoryMeals.apply {
            layoutManager =
                GridLayoutManager(this@CategoryMealsActivity, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter

        }
        categoryMealsAdapter.onClickItem = { meal ->
            Intent(this, MealActivity::class.java).apply {
                putExtra(MEAL_ID,meal.idMeal)
                startActivity(this)
            }
        }
    }

    private fun setUpLiveData() {
        val mealClicked = intent.getStringExtra(HomeFragment.CATEGORY_MEAL)
        mealClicked?.let {
            mealCategoryVM.getMealByCategory(it)

        }
        mealCategoryVM.observeCategorizedMealsLiveData().observe(this){ list->
            binding.tvCategoryCount.text = "Meal Count: ${list.size}"
            categoryMealsAdapter.setMeals(list as ArrayList)

        }
    }
}