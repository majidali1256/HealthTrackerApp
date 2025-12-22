package com.healthtracker.app.ui.medications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.healthtracker.app.data.local.entities.Medication
import com.healthtracker.app.databinding.ItemMedicationBinding
import java.text.SimpleDateFormat
import java.util.Locale

class MedicationAdapter(
    private val onMedicationClick: (Medication) -> Unit,
    private val onTakenClick: (Medication) -> Unit
) : ListAdapter<Medication, MedicationAdapter.MedicationViewHolder>(MedicationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationViewHolder {
        val binding = ItemMedicationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MedicationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicationViewHolder, position: Int) {
        holder.bind(getItem(position), onMedicationClick, onTakenClick)
    }

    class MedicationViewHolder(
        private val binding: ItemMedicationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        private val timeFormatter = SimpleDateFormat("h:mm a", Locale.getDefault())

        fun bind(
            medication: Medication,
            onMedicationClick: (Medication) -> Unit,
            onTakenClick: (Medication) -> Unit
        ) {
            binding.tvMedicationName.text = medication.name
            binding.tvDosage.text = medication.dosage
            binding.tvFrequency.text = medication.frequency
            
            medication.nextDoseTime?.let {
                binding.tvNextDose.text = "Next: ${timeFormatter.format(it)}"
            } ?: run {
                binding.tvNextDose.text = "Schedule not set"
            }
            
            // Refill warning
            if (medication.quantityRemaining != null && medication.quantityRemaining!! <= 5) {
                binding.tvRefillWarning.visibility = android.view.View.VISIBLE
                binding.tvRefillWarning.text = "⚠️ Only ${medication.quantityRemaining} left"
            } else {
                binding.tvRefillWarning.visibility = android.view.View.GONE
            }
            
            binding.root.setOnClickListener { onMedicationClick(medication) }
            binding.btnTaken.setOnClickListener { onTakenClick(medication) }
        }
    }
}

class MedicationDiffCallback : DiffUtil.ItemCallback<Medication>() {
    override fun areItemsTheSame(oldItem: Medication, newItem: Medication) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Medication, newItem: Medication) = oldItem == newItem
}
