package com.healthtracker.app.ui.symptoms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.healthtracker.app.R
import com.healthtracker.app.databinding.ItemSymptomBinding

class SymptomAdapter(
    private val onSymptomClick: (Symptom) -> Unit
) : ListAdapter<Symptom, SymptomAdapter.SymptomViewHolder>(SymptomDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomViewHolder {
        val binding = ItemSymptomBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SymptomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SymptomViewHolder, position: Int) {
        holder.bind(getItem(position), onSymptomClick)
    }

    class SymptomViewHolder(
        private val binding: ItemSymptomBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(symptom: Symptom, onSymptomClick: (Symptom) -> Unit) {
            binding.tvSymptomName.text = symptom.name
            binding.tvSymptomDescription.text = symptom.description
            binding.checkbox.isChecked = symptom.isSelected
            
            // Update card appearance based on selection
            if (symptom.isSelected) {
                binding.cardSymptom.setCardBackgroundColor(
                    itemView.context.getColor(R.color.primary_blue_light)
                )
                binding.cardSymptom.strokeColor = itemView.context.getColor(R.color.primary_blue)
                binding.cardSymptom.strokeWidth = 2
            } else {
                binding.cardSymptom.setCardBackgroundColor(
                    itemView.context.getColor(R.color.card_background)
                )
                binding.cardSymptom.strokeWidth = 0
            }
            
            binding.root.setOnClickListener { onSymptomClick(symptom) }
            binding.checkbox.setOnClickListener { onSymptomClick(symptom) }
        }
    }
}

class SymptomDiffCallback : DiffUtil.ItemCallback<Symptom>() {
    override fun areItemsTheSame(oldItem: Symptom, newItem: Symptom) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Symptom, newItem: Symptom) = oldItem == newItem
}
