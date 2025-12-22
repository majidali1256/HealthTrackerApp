package com.healthtracker.app.ui.documents

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthtracker.app.data.local.entities.Document
import com.healthtracker.app.data.repository.HealthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DocumentVaultViewModel @Inject constructor(
    private val healthRepository: HealthRepository
) : ViewModel() {

    private val _documents = MutableStateFlow<List<Document>>(emptyList())
    val documents: StateFlow<List<Document>> = _documents
    
    private val _uiState = MutableStateFlow(DocumentVaultUiState())
    val uiState: StateFlow<DocumentVaultUiState> = _uiState

    fun loadDocuments(userId: String) {
        viewModelScope.launch {
            healthRepository.getDocumentsFlow(userId).collect { docs ->
                _documents.value = docs
            }
        }
    }

    fun uploadDocument(uri: Uri) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                healthRepository.uploadDocument(uri)
                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to upload document: ${e.message}"
                )
            }
        }
    }

    fun openDocument(document: Document) {
        // Open document viewer
    }

    fun deleteDocument(document: Document) {
        viewModelScope.launch {
            try {
                healthRepository.deleteDocument(document)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to delete: ${e.message}"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class DocumentVaultUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)
