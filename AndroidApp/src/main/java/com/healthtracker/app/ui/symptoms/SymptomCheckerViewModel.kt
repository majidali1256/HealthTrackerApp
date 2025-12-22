package com.healthtracker.app.ui.symptoms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SymptomCheckerViewModel @Inject constructor() : ViewModel() {

    private val _symptoms = MutableStateFlow<List<Symptom>>(emptyList())
    val symptoms: StateFlow<List<Symptom>> = _symptoms
    
    private val _uiState = MutableStateFlow(SymptomCheckerUiState())
    val uiState: StateFlow<SymptomCheckerUiState> = _uiState

    fun loadSymptoms() {
        _symptoms.value = listOf(
            Symptom("headache", "Headache", "Pain in head or face", false),
            Symptom("fever", "Fever", "Elevated body temperature", false),
            Symptom("cough", "Cough", "Persistent coughing", false),
            Symptom("fatigue", "Fatigue", "Unusual tiredness or weakness", false),
            Symptom("nausea", "Nausea", "Feeling sick to stomach", false),
            Symptom("sore_throat", "Sore Throat", "Pain or irritation in throat", false),
            Symptom("body_aches", "Body Aches", "Muscle or joint pain", false),
            Symptom("shortness_breath", "Shortness of Breath", "Difficulty breathing", false),
            Symptom("dizziness", "Dizziness", "Feeling lightheaded or unsteady", false),
            Symptom("chest_pain", "Chest Pain", "Pain or discomfort in chest", false),
            Symptom("abdominal_pain", "Abdominal Pain", "Pain in stomach area", false),
            Symptom("runny_nose", "Runny Nose", "Nasal congestion or discharge", false)
        )
    }

    fun toggleSymptom(symptom: Symptom) {
        _symptoms.value = _symptoms.value.map {
            if (it.id == symptom.id) it.copy(isSelected = !it.isSelected)
            else it
        }
        
        _uiState.value = _uiState.value.copy(
            selectedCount = _symptoms.value.count { it.isSelected }
        )
    }

    fun clearSelection() {
        _symptoms.value = _symptoms.value.map { it.copy(isSelected = false) }
        _uiState.value = _uiState.value.copy(
            selectedCount = 0,
            analysisResult = null
        )
    }

    fun analyzeSymptoms() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isAnalyzing = true)
            
            // Simulate AI analysis delay
            delay(1500)
            
            val selected = _symptoms.value.filter { it.isSelected }
            val result = generateAnalysis(selected)
            
            _uiState.value = _uiState.value.copy(
                isAnalyzing = false,
                analysisResult = result
            )
        }
    }

    private fun generateAnalysis(symptoms: List<Symptom>): SymptomAnalysisResult {
        val symptomIds = symptoms.map { it.id }
        
        // Simple rule-based analysis (in production, use ML model)
        return when {
            symptomIds.contains("chest_pain") || symptomIds.contains("shortness_breath") -> {
                SymptomAnalysisResult(
                    condition = "Possible Cardiac or Respiratory Issue",
                    severity = "Severe",
                    advice = "Seek immediate medical attention. These symptoms could indicate a serious condition requiring urgent evaluation."
                )
            }
            symptomIds.containsAll(listOf("fever", "cough", "body_aches")) -> {
                SymptomAnalysisResult(
                    condition = "Possible Flu or Viral Infection",
                    severity = "Moderate",
                    advice = "Rest, stay hydrated, and monitor your temperature. Consider seeing a doctor if symptoms worsen or persist beyond 3 days."
                )
            }
            symptomIds.containsAll(listOf("headache", "fever")) -> {
                SymptomAnalysisResult(
                    condition = "Possible Viral Infection",
                    severity = "Moderate",
                    advice = "Rest and take over-the-counter pain relievers. Monitor for worsening symptoms and seek medical care if fever exceeds 103°F."
                )
            }
            symptomIds.contains("headache") && symptomIds.contains("dizziness") -> {
                SymptomAnalysisResult(
                    condition = "Possible Dehydration or Migraine",
                    severity = "Mild",
                    advice = "Drink plenty of water and rest in a dark, quiet room. If symptoms persist, consult a healthcare provider."
                )
            }
            else -> {
                SymptomAnalysisResult(
                    condition = "General Discomfort",
                    severity = "Mild",
                    advice = "Monitor your symptoms and rest. If they persist or worsen over the next 24-48 hours, consult a healthcare provider."
                )
            }
        }
    }
}

data class Symptom(
    val id: String,
    val name: String,
    val description: String,
    val isSelected: Boolean
)

data class SymptomCheckerUiState(
    val isAnalyzing: Boolean = false,
    val selectedCount: Int = 0,
    val analysisResult: SymptomAnalysisResult? = null
)

data class SymptomAnalysisResult(
    val condition: String,
    val severity: String,
    val advice: String
)
