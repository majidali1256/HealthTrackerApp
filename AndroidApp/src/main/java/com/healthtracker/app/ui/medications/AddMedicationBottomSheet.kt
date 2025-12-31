package com.healthtracker.app.ui.medications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.auth.FirebaseAuth
import com.healthtracker.app.R
import com.healthtracker.app.data.local.entities.Medication
import com.healthtracker.app.data.repository.HealthRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

/**
 * Bottom sheet for adding new medications with name, dosage, frequency, and reminder time.
 */
@AndroidEntryPoint
class AddMedicationBottomSheet : BottomSheetDialogFragment() {
    
    @Inject
    lateinit var healthRepository: HealthRepository
    
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    
    private var selectedHour: Int = 9
    private var selectedMinute: Int = 0
    private var onMedicationAdded: (() -> Unit)? = null
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_add_medication, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val etName = view.findViewById<TextInputEditText>(R.id.etMedicationName)
        val etDosage = view.findViewById<TextInputEditText>(R.id.etDosage)
        val spinnerFrequency = view.findViewById<MaterialAutoCompleteTextView>(R.id.spinnerFrequency)
        val btnSelectTime = view.findViewById<MaterialButton>(R.id.btnSelectTime)
        val tvSelectedTime = view.findViewById<android.widget.TextView>(R.id.tvSelectedTime)
        val btnSave = view.findViewById<MaterialButton>(R.id.btnSaveMedication)
        
        // Setup frequency dropdown
        val frequencies = arrayOf("Once daily", "Twice daily", "Three times daily", "As needed")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, frequencies)
        spinnerFrequency.setAdapter(adapter)
        spinnerFrequency.setText(frequencies[0], false)
        
        // Display default time
        updateTimeDisplay(tvSelectedTime)
        
        // Time picker
        btnSelectTime.setOnClickListener {
            showTimePicker(tvSelectedTime)
        }
        
        // Save button
        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val dosage = etDosage.text.toString().trim()
            val frequency = spinnerFrequency.text.toString()
            
            if (name.isEmpty()) {
                etName.error = "Required"
                return@setOnClickListener
            }
            if (dosage.isEmpty()) {
                etDosage.error = "Required"
                return@setOnClickListener
            }
            
            saveMedication(name, dosage, frequency)
        }
    }
    
    private fun showTimePicker(tvSelectedTime: android.widget.TextView) {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(selectedHour)
            .setMinute(selectedMinute)
            .setTitleText("Set reminder time")
            .build()
        
        picker.show(parentFragmentManager, "medication_time_picker")
        
        picker.addOnPositiveButtonClickListener {
            selectedHour = picker.hour
            selectedMinute = picker.minute
            updateTimeDisplay(tvSelectedTime)
        }
    }
    
    private fun updateTimeDisplay(tvSelectedTime: android.widget.TextView) {
        val amPm = if (selectedHour < 12) "AM" else "PM"
        val hour12 = when {
            selectedHour == 0 -> 12
            selectedHour > 12 -> selectedHour - 12
            else -> selectedHour
        }
        tvSelectedTime.text = String.format("%d:%02d %s", hour12, selectedMinute, amPm)
    }
    
    private fun saveMedication(name: String, dosage: String, frequency: String) {
        val userId = firebaseAuth.currentUser?.uid ?: return
        
        // Format schedule time as HH:mm
        val scheduleTime = String.format("%02d:%02d", selectedHour, selectedMinute)
        
        // Map frequency string to constant
        val frequencyConst = when (frequency) {
            "Once daily" -> "ONCE_DAILY"
            "Twice daily" -> "TWICE_DAILY"
            "Three times daily" -> "THREE_TIMES_DAILY"
            "As needed" -> "AS_NEEDED"
            else -> "ONCE_DAILY"
        }
        
        val medication = Medication(
            userId = userId,
            name = name,
            dosage = dosage.substringBefore(" ").ifEmpty { dosage },
            dosageUnit = dosage.substringAfter(" ").ifEmpty { "tablet" },
            frequency = frequencyConst,
            scheduleTimes = scheduleTime,
            startDate = java.util.Date(),
            isActive = true,
            createdAt = java.util.Date()
        )
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                healthRepository.addMedication(medication)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "✓ $name added with reminder at ${selectedHour}:${String.format("%02d", selectedMinute)}", Toast.LENGTH_SHORT).show()
                    onMedicationAdded?.invoke()
                    dismiss()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    fun setOnMedicationAddedListener(listener: () -> Unit) {
        onMedicationAdded = listener
    }
    
    companion object {
        const val TAG = "AddMedicationBottomSheet"
        
        fun newInstance(): AddMedicationBottomSheet {
            return AddMedicationBottomSheet()
        }
    }
}
