package com.healthtracker.app.ui.medications

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.auth.FirebaseAuth
import com.healthtracker.app.databinding.ActivityMedicationsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Medication management screen with scheduling and reminders.
 */
@AndroidEntryPoint
class MedicationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMedicationsBinding
    private val viewModel: MedicationsViewModel by viewModels()
    private lateinit var medicationAdapter: MedicationAdapter
    
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupClickListeners()
        observeViewModel()
        
        firebaseAuth.currentUser?.uid?.let { userId ->
            viewModel.loadMedications(userId)
        }
    }

    private fun setupUI() {
        medicationAdapter = MedicationAdapter(
            onMedicationClick = { medication ->
                // Show medication detail
            },
            onTakenClick = { medication ->
                viewModel.markAsTaken(medication)
                Toast.makeText(this, "${medication.name} marked as taken", Toast.LENGTH_SHORT).show()
            }
        )
        
        binding.rvMedications.apply {
            layoutManager = LinearLayoutManager(this@MedicationsActivity)
            adapter = medicationAdapter
        }
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener { finish() }
        
        binding.fabAddMedication.setOnClickListener {
            showAddMedicationDialog()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.medications.collect { medications ->
                        medicationAdapter.submitList(medications)
                        binding.emptyState.visibility = if (medications.isEmpty()) View.VISIBLE else View.GONE
                        binding.rvMedications.visibility = if (medications.isEmpty()) View.GONE else View.VISIBLE
                    }
                }
                
                launch {
                    viewModel.uiState.collect { state ->
                        binding.tvNextDose.text = state.nextDoseText
                    }
                }
            }
        }
    }

    private fun showAddMedicationDialog() {
        val bottomSheet = AddMedicationBottomSheet.newInstance()
        bottomSheet.setOnMedicationAddedListener {
            // Refresh medications list
            firebaseAuth.currentUser?.uid?.let { userId ->
                viewModel.loadMedications(userId)
            }
        }
        bottomSheet.show(supportFragmentManager, AddMedicationBottomSheet.TAG)
    }
}
