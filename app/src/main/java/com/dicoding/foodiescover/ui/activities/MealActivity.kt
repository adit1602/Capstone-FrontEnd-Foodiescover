package com.dicoding.foodiescover.ui.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.foodiescover.databinding.ActivityMealBinding
import com.dicoding.foodiescover.db.MealDatabase
import com.dicoding.foodiescover.models.Meal
import com.dicoding.foodiescover.repo.MealRepository
import com.dicoding.foodiescover.viewmodels.meal.MealViewModel
import com.dicoding.foodiescover.viewmodels.meal.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMealBinding
    private lateinit var mealID : String

    lateinit var mealActivityVM : MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealRepository = MealRepository(MealDatabase.getInstance(this).getMealDao())
        val mealViewModelFactory =  MealViewModelFactory(mealRepository)
        mealActivityVM = ViewModelProvider(this, mealViewModelFactory)[MealViewModel::class.java]
        setUI()

    }

    private fun setUI(){
        //get id
        intent?.let {
            mealID = intent.getStringExtra("MEAL_ID")!!
        }

        onLoadingState()
        mealActivityVM.getMealData(mealID)
        mealActivityVM.observeMealData().observe(this
        ) { meal ->
            onSuccessState()
            meal?.let {
                attachDataToView(it)
            }
        }
    }

    private fun attachDataToView(meal: Meal) {
        binding.apply {
            tvCategory.text = "Category: ${meal.strCategory}"
            tvLocation.text = "Location: ${meal.strArea}"
            tvInstruction.text = meal.strInstructions
            clToolbar.title = meal.strMeal
            Glide.with(this@MealActivity).load(meal.strMealThumb).into(ivMealImageDetail)

            ivYoutube.setOnClickListener {
                launchYoutube(meal.strYoutube!!)
            }
            btnAddToFavorites.setOnClickListener {
                mealActivityVM.addMeal(meal)
                Toast.makeText(this@MealActivity, "Added to favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun launchYoutube(url : String){
        Intent(Intent.ACTION_VIEW, Uri.parse(url)).also {
            startActivity(it)
        }
    }

    private fun onLoadingState(){
        binding.apply {
            //show loading
            progressBar.visibility = View.VISIBLE
            tvCategory.visibility = View.INVISIBLE
            tvLocation.visibility = View.INVISIBLE
            tvInstruction.visibility = View.INVISIBLE
            tvPreparation.visibility = View.INVISIBLE
            btnAddToFavorites.visibility = View.INVISIBLE
            ivYoutube.visibility = View.INVISIBLE

        }
    }

    private fun onSuccessState(){
        binding.apply {
            progressBar.visibility = View.INVISIBLE
            tvCategory.visibility = View.VISIBLE
            tvLocation.visibility = View.VISIBLE
            tvInstruction.visibility = View.VISIBLE
            tvPreparation.visibility = View.VISIBLE
            btnAddToFavorites.visibility = View.VISIBLE
            ivYoutube.visibility = View.VISIBLE
        }

    }

}