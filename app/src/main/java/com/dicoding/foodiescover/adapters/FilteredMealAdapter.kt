package com.dicoding.foodiescover.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.foodiescover.databinding.FilteredMealItemBinding
import com.dicoding.foodiescover.models.Meal

class FilteredMealAdapter : RecyclerView.Adapter<FilteredMealAdapter.MealViewHolder>() {
    private var list = ArrayList<Meal>()
    var onItemClick : ((Meal) -> Unit)? = null

    fun setList(list : ArrayList<Meal>){
        this.list = list
        notifyDataSetChanged()
    }


    inner class MealViewHolder(val binding : FilteredMealItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            FilteredMealItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = list[position]
        holder.binding.apply {
            tvFilteredMealItemName.text = meal.strMeal
            Glide
                .with(holder.itemView)
                .load(meal.strMealThumb)
                .into(ivFilteredMealItem)
        }
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(meal)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}