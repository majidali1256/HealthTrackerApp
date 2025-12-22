package com.healthtracker.app.ui.medications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthtracker.app.data.local.entities.Medication
import com.healthtracker.app.data.repository.HealthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MedicationsViewModel @Inject constructor(
    private val healthRepository: HealthRepository
) : ViewModel() {

    private val _medications = MutableStateFlow<List<Medication>>(emptyList())
    val medications: StateFlow<List<Medication>> = _medications
    
    private val _uiState = MutableStateFlow(MedicationsUiState())
    val uiState: StateFlow<MedicationsUiState> = _uiState

    fun loadMedications(userId: String) {
        viewModelScope.launch {
            healthRepository.getActiveMedicationsFlow(userId).collect { meds ->
                _medications.value = meds
                
                // Find next dose
                val nextDose = meds
                    .filter { it.nextDoseTime != null && it.nextDoseTime!! > Date() }
                    .minByOrNull { it.nextDoseTime!! }
                
                _uiState.value = _uiState.value.copy(
                    nextDoseText = nextDose?.let {
                        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
                        "Next: ${it.name} at ${timeFormat.format(it.nextDoseTime!!)}"
                    } ?: "No upcoming doses"
                )
            }
        }
    }

    fun markAsTaken(medication: Medication) {
        viewModelScope.launch {
            healthRepository.recordMedicationTaken(medication.id)
        }
    }
}

data class MedicationsUiState(
    val nextDoseText: String = "No upcoming doses"
)
