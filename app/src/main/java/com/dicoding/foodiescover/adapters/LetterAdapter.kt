package com.dicoding.foodiescover.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.foodiescover.databinding.LetterItemBinding

class LetterAdapter : RecyclerView.Adapter<LetterAdapter.ViewHolder>(){
    private var list = ArrayList<Char>()
    var onItemClick : ((Char) -> Unit)? = null


    inner class ViewHolder(val binding : LetterItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LetterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val letter = list[position]
        holder.binding.apply {
            tvLetter.text = letter.toString()
        }
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(letter)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list : ArrayList<Char>){
        this.list = list
    }

}