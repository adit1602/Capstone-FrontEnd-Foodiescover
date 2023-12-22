package com.dicoding.foodiescover.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.foodiescover.adapters.CategoryAdapter
import com.dicoding.foodiescover.adapters.PopularMealAdapter
import com.dicoding.foodiescover.databinding.FragmentHomeBinding
import com.dicoding.foodiescover.models.Category
import com.dicoding.foodiescover.models.Meal
import com.dicoding.foodiescover.models.MealPopular
import com.dicoding.foodiescover.ui.activities.CategoryMealsActivity
import com.dicoding.foodiescover.ui.activities.MealActivity
import com.dicoding.foodiescover.viewmodels.home.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var homeFragmentVM : HomeViewModel
    private lateinit var randomMealRef : Meal
    private lateinit var popularMealAdapter : PopularMealAdapter
    private lateinit var categoryAdapter : CategoryAdapter
    private val MEAL_ID = "MEAL_ID"

    val TAG = "HomeFragment"

    companion object{
        const val CATEGORY_MEAL = "com.dicoding.foodiescover.ui.fragments.category"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeFragmentVM = ViewModelProvider(this)[HomeViewModel::class.java]
        popularMealAdapter = PopularMealAdapter()
        categoryAdapter = CategoryAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeFragmentVM.getRandomMeal()
        observeRandomMeal()

        binding.ivRandomMeal.setOnClickListener {
            openMealActivity()
        }

        homeFragmentVM.getPopularItemsByCategory()
        setUpPopularMealsRecyclerView()

        getMealCategories()
        setUpCategoryRecyclerView()



    }

    private fun setUpCategoryRecyclerView(){
        observeMealCategories()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL,false)
            adapter = categoryAdapter
        }

        categoryAdapter.onItemClick = { category ->
            Intent(activity, CategoryMealsActivity::class.java).apply{
                putExtra(CATEGORY_MEAL, category.strCategory)
                startActivity(this)
            }
        }

    }

    private fun setUpPopularMealsRecyclerView() {
        observePopularMealsByCategory()
        binding.rvPopularMeals.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularMealAdapter
        }

        popularMealAdapter.onItemClick = { meal->
            Intent(activity, MealActivity::class.java).apply {
                putExtra("MEAL_ID", meal.idMeal)
                startActivity(this)
            }
        }
    }

    private fun getMealCategories(){
        homeFragmentVM.getMealCategories()
    }

    private fun observeMealCategories(){
        homeFragmentVM.observeMealCategories().observe(viewLifecycleOwner
        ) { categories ->
            categoryAdapter.setCategory(categories as ArrayList<Category>)
        }
    }

    private fun observePopularMealsByCategory() {
        homeFragmentVM.observePopularMealLiveData().observe(viewLifecycleOwner
        ) { list -> popularMealAdapter.setMeals(list as ArrayList<MealPopular>) }
    }

    private fun openMealActivity() {
        Intent(activity, MealActivity::class.java).also{ intent ->
            intent.putExtra(MEAL_ID, randomMealRef.idMeal)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        homeFragmentVM.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            meal?.let { mealResult ->
                setRandomImage(mealResult.strMealThumb!!)
                randomMealRef = meal
            }
        }
    }

    private fun setRandomImage(strMealThumb: String) {
        Glide
            .with(this@HomeFragment)
            .load(strMealThumb)
            .into(binding.ivRandomMeal)
    }


}