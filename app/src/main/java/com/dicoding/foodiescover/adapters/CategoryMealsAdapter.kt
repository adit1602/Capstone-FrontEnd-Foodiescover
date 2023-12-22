package com.dicoding.foodiescover.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.foodiescover.models.Meal
import com.dicoding.foodiescover.databinding.CategorizedMealItemBinding

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.MealViewHolder>(){
    inner class MealViewHolder(var binding : CategorizedMealItemBinding) : RecyclerView.ViewHolder(binding.root)
    private var categorizedMeals = ArrayList<Meal>()
    var onClickItem : ((Meal) -> Unit)? = null

    fun setMeals(list : ArrayList<Meal>){
        this.categorizedMeals = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            CategorizedMealItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = categorizedMeals[position]
        holder.binding.apply {
            //set image
            Glide
                .with(holder.itemView)
                .load(meal.strMealThumb)
                .into(ivCategorizedMeal)
            tvMealName.text = meal.strMeal
        }

        holder.itemView.setOnClickListener {
            onClickItem?.invoke(meal)
        }
    }

    override fun getItemCount(): Int {
        return categorizedMeals.size
    }


}