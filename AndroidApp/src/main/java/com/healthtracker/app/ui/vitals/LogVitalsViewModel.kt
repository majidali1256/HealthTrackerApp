package com.healthtracker.app.ui.vitals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.healthtracker.app.data.local.entities.VitalRecord
import com.healthtracker.app.data.repository.HealthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class LogVitalsViewModel @Inject constructor(
    private val healthRepository: HealthRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _saveState = MutableStateFlow<SaveState>(SaveState.Idle)
    val saveState: StateFlow<SaveState> = _saveState

    fun saveVitals(
        heartRate: Int?,
        systolicBp: Int?,
        diastolicBp: Int?,
        spO2: Int?,
        bloodGlucose: Float?,
        temperatureCelsius: Float?,
        notes: String?
    ) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId == null) {
            _saveState.value = SaveState.Error("Please login first")
            return
        }

        viewModelScope.launch {
            _saveState.value = SaveState.Loading
            try {
                val vitalRecord = VitalRecord(
                    userId = userId,
                    heartRate = heartRate,
                    systolicBp = systolicBp,
                    diastolicBp = diastolicBp,
                    spO2 = spO2,
                    bloodGlucose = bloodGlucose,
                    temperatureCelsius = temperatureCelsius,
                    notes = notes,
                    source = "MANUAL",
                    timestamp = Date(),
                    isSynced = false
                )
                
                healthRepository.addVitalRecord(vitalRecord)
                _saveState.value = SaveState.Success
            } catch (e: Exception) {
                _saveState.value = SaveState.Error(e.message ?: "Failed to save vitals")
            }
        }
    }
}
