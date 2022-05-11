package com.ltu.foody.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ltu.foody.databinding.IngredientsListBinding
import com.ltu.foody.model.Ingredients


class IngredientsListAdapter(): ListAdapter<Ingredients, IngredientsListAdapter.ViewHolder>(IngredientsDiffCallback()) {
    class ViewHolder(private var binding: IngredientsListBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(ingredients: Ingredients){
            binding.ingredients = ingredients
            binding.executePendingBindings()

        }

        companion object {
            fun from (parent: ViewGroup) : ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = IngredientsListBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }
}

class IngredientsDiffCallback : DiffUtil.ItemCallback<Ingredients>(){


    override fun areItemsTheSame(oldItem: Ingredients, newItem: Ingredients): Boolean {
        return oldItem.original == newItem.original
    }

    override fun areContentsTheSame(oldItem: Ingredients, newItem: Ingredients): Boolean {
        return oldItem == newItem
    }

}


