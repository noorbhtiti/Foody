package com.ltu.foody.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ltu.foody.databinding.InstructionsListBinding
import com.ltu.foody.model.InstructionsSteps


class InstructionsListAdapter(): ListAdapter<InstructionsSteps, InstructionsListAdapter.ViewHolder>(InstructionsDiffCallback()) {
    class ViewHolder(private var binding: InstructionsListBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(instructionsSteps: InstructionsSteps){
            binding.instruction = instructionsSteps
            binding.executePendingBindings()

        }

        companion object {
            fun from (parent: ViewGroup) : ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = InstructionsListBinding.inflate(layoutInflater,parent,false)
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

class InstructionsDiffCallback : DiffUtil.ItemCallback<InstructionsSteps>(){


    override fun areItemsTheSame(oldItem: InstructionsSteps, newItem: InstructionsSteps): Boolean {
        return oldItem.step == newItem.step
    }

    override fun areContentsTheSame(oldItem: InstructionsSteps, newItem: InstructionsSteps): Boolean {
        return oldItem == newItem
    }

}

