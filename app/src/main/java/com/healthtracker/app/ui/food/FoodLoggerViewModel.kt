package com.healthtracker.app.ui.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.healthtracker.app.data.local.entities.HealthLog
import com.healthtracker.app.data.local.entities.HealthLogType
import com.healthtracker.app.data.repository.HealthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class FoodLoggerViewModel @Inject constructor(
    private val healthRepository: HealthRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(FoodLoggerUiState())
    val uiState: StateFlow<FoodLoggerUiState> = _uiState

    fun loadTodayNutrition(userId: String) {
        viewModelScope.launch {
            val todayCalories = healthRepository.getTodayCalories(userId)
            _uiState.value = _uiState.value.copy(
                totalCalories = todayCalories?.toInt() ?: 0
            )
        }
        
        viewModelScope.launch {
            val hydration = healthRepository.getTodayHydration(userId)
            _uiState.value = _uiState.value.copy(
                waterGlasses = hydration?.toInt() ?: 0
            )
        }
    }

    fun lookupFood(barcode: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            // Simulated food lookup - in production, call a nutrition API
            val mockFood = when {
                barcode.startsWith("5") -> FoodItem("Banana", 105, 1, 27, 0)
                barcode.startsWith("4") -> FoodItem("Apple", 95, 0, 25, 0)
                barcode.startsWith("3") -> FoodItem("Protein Bar", 220, 20, 25, 8)
                else -> FoodItem("Unknown Food", 100, 5, 15, 3)
            }
            
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                scannedFood = mockFood
            )
            
            // Log the food
            logFood(mockFood)
        }
    }

    private fun logFood(food: FoodItem) {
        viewModelScope.launch {
            val userId = firebaseAuth.currentUser?.uid ?: return@launch
            
            val healthLog = HealthLog(
                userId = userId,
                type = HealthLogType.NUTRITION,
                title = food.name,
                value = food.calories.toFloat(),
                unit = "kcal",
                timestamp = Date()
            )
            
            healthRepository.insertHealthLog(healthLog)
            
            // Update state
            _uiState.value = _uiState.value.copy(
                totalCalories = _uiState.value.totalCalories + food.calories,
                protein = _uiState.value.protein + food.protein,
                carbs = _uiState.value.carbs + food.carbs,
                fat = _uiState.value.fat + food.fat
            )
        }
    }

    fun logWater() {
        viewModelScope.launch {
            val userId = firebaseAuth.currentUser?.uid ?: return@launch
            
            val healthLog = HealthLog(
                userId = userId,
                type = HealthLogType.HYDRATION,
                value = 1f,
                unit = "glass",
                timestamp = Date()
            )
            
            healthRepository.insertHealthLog(healthLog)
            
            _uiState.value = _uiState.value.copy(
                waterGlasses = _uiState.value.waterGlasses + 1
            )
        }
    }
}

data class FoodLoggerUiState(
    val isLoading: Boolean = false,
    val totalCalories: Int = 0,
    val caloriesGoal: Int = 2000,
    val protein: Int = 0,
    val carbs: Int = 0,
    val fat: Int = 0,
    val waterGlasses: Int = 0,
    val scannedFood: FoodItem? = null
)

data class FoodItem(
    val name: String,
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int
)
