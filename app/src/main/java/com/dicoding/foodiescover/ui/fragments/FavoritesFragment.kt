package com.dicoding.foodiescover.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.foodiescover.adapters.FavoritesAdapter
import com.dicoding.foodiescover.databinding.FragmentFavoritesBinding
import com.dicoding.foodiescover.db.MealDatabase
import com.dicoding.foodiescover.repo.MealRepository
import com.dicoding.foodiescover.ui.activities.MealActivity
import com.dicoding.foodiescover.viewmodels.favorites.FavoritesViewModel
import com.dicoding.foodiescover.viewmodels.favorites.FavoritesViewModelFactory

class FavoritesFragment : Fragment() {

    private lateinit var binding : FragmentFavoritesBinding
    private lateinit var favoriteVM : FavoritesViewModel
    private lateinit var favoritesAdapter : FavoritesAdapter
    private val MEAL_ID = "MEAL_ID"

    //meal list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //repo
        val mealRepository = MealRepository(MealDatabase.getInstance(requireActivity()).getMealDao())
        //factory
        val favoritesViewModelFactory = FavoritesViewModelFactory(mealRepository)
        favoriteVM = ViewModelProvider(this, favoritesViewModelFactory)[FavoritesViewModel::class.java]
        favoritesAdapter = FavoritesAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteVM.meals.observe(viewLifecycleOwner, Observer { list ->
            favoritesAdapter.differ.submitList(list)
        })

        setUpFavoritesRecyclerView()

    }

    private fun setUpFavoritesRecyclerView(){
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(activity, 2,GridLayoutManager.VERTICAL,false)
            adapter = favoritesAdapter
        }

        //item clicks
        favoritesAdapter.onItemClick = { meal ->
            Intent(activity, MealActivity::class.java).apply {
                putExtra(MEAL_ID, meal.idMeal)
                startActivity(this)
            }
        }

        favoritesAdapter.onDeleteItemClick = { meal ->
            favoriteVM.removeMeal(meal)
            Toast.makeText(activity, "${meal.strMeal} deleted", Toast.LENGTH_SHORT).show()
        }



    }
}