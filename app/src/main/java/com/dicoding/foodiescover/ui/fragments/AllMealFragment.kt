package com.dicoding.foodiescover.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.foodiescover.adapters.FilteredMealAdapter
import com.dicoding.foodiescover.adapters.LetterAdapter
import com.dicoding.foodiescover.databinding.FragmentAllMealsBinding
import com.dicoding.foodiescover.models.Meal
import com.dicoding.foodiescover.ui.activities.MealActivity
import com.dicoding.foodiescover.viewmodels.AllMealViewHolder

class AllMealFragment : Fragment() {
    lateinit var binding : FragmentAllMealsBinding
    lateinit var lettersAdapter : LetterAdapter
    lateinit var filteredMealAdapter : FilteredMealAdapter
    lateinit var allMealVM : AllMealViewHolder
    private val MEAL_ID = "MEAL_ID"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lettersAdapter = LetterAdapter()
        filteredMealAdapter = FilteredMealAdapter()
        allMealVM = AllMealViewHolder()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLetterRecyclerView()
        setUpFilteredMealRecyclerView()

    }

    private fun setUpLetterRecyclerView() {
        val alphabets = mutableListOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R',  'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z')
        lettersAdapter.setList(alphabets as ArrayList)

        binding.rvLetters.apply {
            layoutManager = GridLayoutManager(activity, 7, GridLayoutManager.VERTICAL,false)
            adapter = lettersAdapter
        }

        lettersAdapter.onItemClick = { char ->
            observeFilteredMealLiveData(char)
        }
    }

    private fun setUpFilteredMealRecyclerView(){
        //initialize
        observeFilteredMealLiveData('A')
        binding.rvMealsByLetter.apply {
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
            adapter = filteredMealAdapter
        }

        filteredMealAdapter.onItemClick = { meal ->
            Intent(activity, MealActivity::class.java).apply {
                putExtra(MEAL_ID, meal.idMeal)
                startActivity(this)
            }
        }

    }

    private fun observeFilteredMealLiveData(char : Char) {
        //initialize
        allMealVM.getMealByFirstLetter(char)
        allMealVM.observeFilteredMealListLiveData().observe(viewLifecycleOwner) { mealList ->
            mealList?.let {
                filteredMealAdapter.setList(mealList as ArrayList<Meal>)
                binding.tvFilterCount.text = "Meal Count: ${mealList.size}"
                binding.tvCurrentLetter.text = "Now Browsing: $char"
            }
        }
    }

}