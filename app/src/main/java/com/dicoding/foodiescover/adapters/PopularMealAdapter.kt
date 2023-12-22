package com.dicoding.foodiescover.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.foodiescover.databinding.PopularMealItemBinding
import com.dicoding.foodiescover.models.MealPopular

class PopularMealAdapter : RecyclerView.Adapter<PopularMealAdapter.MealViewHolder>(){
    private var list = ArrayList<MealPopular>()
    lateinit var onItemClick : ((MealPopular) -> Unit)

    inner class MealViewHolder(var binding : PopularMealItemBinding) : RecyclerView.ViewHolder(binding.root){

    }

    fun setMeals(list : ArrayList<MealPopular>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            PopularMealItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = list[position]
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.ivPopularItem)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(meal)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}