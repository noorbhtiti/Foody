package com.ltu.foody.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ltu.foody.databinding.MealsListBinding
import com.ltu.foody.model.Recipes


class MealListAdapter(private val mealClickListener: MealListClickListener): ListAdapter<Recipes, MealListAdapter.ViewHolder>(MealListDiffCallback()) {
    class ViewHolder(private var binding: MealsListBinding) :RecyclerView.ViewHolder(binding.root){

        fun bind(recipes: Recipes,mealListClickListener:MealListClickListener){
            binding.recipe = recipes
            binding.clickListener = mealListClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from (parent: ViewGroup) : ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MealsListBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(getItem(position), mealClickListener)
    }
}

class MealListDiffCallback : DiffUtil.ItemCallback<Recipes>(){

    override fun areItemsTheSame(oldItem: Recipes, newItem: Recipes): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipes, newItem: Recipes): Boolean {
        return oldItem == newItem
    }

}

class MealListClickListener (val clickListener: (recipes: Recipes)-> Unit){
    fun onClick(recipes: Recipes) = clickListener(recipes)
}