package com.healthtracker.app.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.healthtracker.app.R
import com.healthtracker.app.data.local.entities.HealthLog
import com.healthtracker.app.data.local.entities.HealthLogType
import com.healthtracker.app.data.repository.HealthRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Bottom sheet for quick logging of health data.
 * Allows users to quickly log water, nutrition, exercise, or symptoms.
 */
@AndroidEntryPoint
class QuickLogBottomSheet : BottomSheetDialogFragment() {
    
    @Inject
    lateinit var healthRepository: HealthRepository
    
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    
    private var onLogSaved: (() -> Unit)? = null
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_quick_log, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val btnLogWater = view.findViewById<MaterialButton>(R.id.btnLogWater)
        val btnLogSteps = view.findViewById<MaterialButton>(R.id.btnLogSteps)
        val btnLogSleep = view.findViewById<MaterialButton>(R.id.btnLogSleep)
        val btnLogVitals = view.findViewById<MaterialButton>(R.id.btnLogVitals)
        val tilLogValue = view.findViewById<TextInputLayout>(R.id.tilLogValue)
        val etValue = view.findViewById<TextInputEditText>(R.id.etLogValue)
        val btnSave = view.findViewById<MaterialButton>(R.id.btnSaveLog)
        
        var selectedType: String? = null
        
        fun updateButtonStates(selected: MaterialButton) {
            listOf(btnLogWater, btnLogSteps, btnLogSleep, btnLogVitals).forEach { btn ->
                btn.isSelected = btn == selected
                btn.alpha = if (btn == selected) 1.0f else 0.6f
            }
            tilLogValue.visibility = View.VISIBLE
            btnSave.visibility = View.VISIBLE
        }
        
        btnLogWater.setOnClickListener {
            selectedType = HealthLogType.HYDRATION
            etValue.hint = "Glasses of water"
            updateButtonStates(btnLogWater)
        }
        
        btnLogSteps.setOnClickListener {
            selectedType = HealthLogType.EXERCISE
            etValue.hint = "Number of steps"
            updateButtonStates(btnLogSteps)
        }
        
        btnLogSleep.setOnClickListener {
            selectedType = HealthLogType.NOTE
            etValue.hint = "Hours of sleep"
            updateButtonStates(btnLogSleep)
        }
        
        btnLogVitals.setOnClickListener {
            selectedType = HealthLogType.SYMPTOM
            etValue.hint = "Heart rate (BPM)"
            updateButtonStates(btnLogVitals)
        }
        
        btnSave.setOnClickListener {
            val value = etValue.text.toString().toFloatOrNull()
            if (value == null || selectedType == null) {
                Toast.makeText(context, "Please enter a valid value", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            val userId = firebaseAuth.currentUser?.uid ?: return@setOnClickListener
            
            saveLog(userId, selectedType!!, value)
        }
    }
    
    private fun saveLog(userId: String, type: String, value: Float) {
        val (title, unit, description) = when (type) {
            HealthLogType.HYDRATION -> Triple("Water intake", "glasses", "${value.toInt()} glass(es) of water")
            HealthLogType.EXERCISE -> Triple("Steps logged", "steps", "${value.toInt()} steps")
            HealthLogType.NOTE -> Triple("Sleep logged", "hours", "${value} hours of sleep")
            HealthLogType.SYMPTOM -> Triple("Heart rate", "BPM", "${value.toInt()} BPM")
            else -> Triple("Health log", "", "$value")
        }
        
        val healthLog = HealthLog(
            userId = userId,
            type = type,
            value = value,
            unit = unit,
            title = title,
            description = description
        )
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                healthRepository.addHealthLog(healthLog)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "✓ Logged successfully", Toast.LENGTH_SHORT).show()
                    onLogSaved?.invoke()
                    dismiss()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Failed to save: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    fun setOnLogSavedListener(listener: () -> Unit) {
        onLogSaved = listener
    }
    
    companion object {
        const val TAG = "QuickLogBottomSheet"
        
        fun newInstance(): QuickLogBottomSheet {
            return QuickLogBottomSheet()
        }
    }
}
