package com.healthtracker.app.ui.symptoms

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.healthtracker.app.databinding.ActivitySymptomCheckerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * AI-driven Symptom Checker.
 * Guides users through symptom selection and provides preliminary insights.
 */
@AndroidEntryPoint
class SymptomCheckerActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySymptomCheckerBinding
    private val viewModel: SymptomCheckerViewModel by viewModels()
    private lateinit var symptomAdapter: SymptomAdapter
    
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySymptomCheckerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupUI() {
        symptomAdapter = SymptomAdapter { symptom ->
            viewModel.toggleSymptom(symptom)
        }
        
        binding.rvSymptoms.apply {
            layoutManager = LinearLayoutManager(this@SymptomCheckerActivity)
            adapter = symptomAdapter
        }
        
        // Load common symptoms
        viewModel.loadSymptoms()
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener { finish() }
        
        binding.btnAnalyze.setOnClickListener {
            viewModel.analyzeSymptoms()
        }
        
        binding.btnClear.setOnClickListener {
            viewModel.clearSelection()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.symptoms.collect { symptoms ->
                        symptomAdapter.submitList(symptoms)
                    }
                }
                
                launch {
                    viewModel.uiState.collect { state ->
                        binding.progressBar.visibility = if (state.isAnalyzing) View.VISIBLE else View.GONE
                        binding.btnAnalyze.isEnabled = state.selectedCount > 0 && !state.isAnalyzing
                        binding.tvSelectedCount.text = "${state.selectedCount} symptoms selected"
                        
                        // Show result
                        if (state.analysisResult != null) {
                            showAnalysisResult(state.analysisResult)
                        }
                    }
                }
            }
        }
    }

    private fun showAnalysisResult(result: SymptomAnalysisResult) {
        binding.cardResult.visibility = View.VISIBLE
        binding.tvResultTitle.text = result.condition
        binding.tvResultSeverity.text = result.severity
        binding.tvResultAdvice.text = result.advice
        
        // Color based on severity
        val severityColor = when (result.severity) {
            "Mild" -> getColor(com.healthtracker.app.R.color.success_green)
            "Moderate" -> getColor(com.healthtracker.app.R.color.warning_orange)
            "Severe" -> getColor(com.healthtracker.app.R.color.alert_red)
            else -> getColor(com.healthtracker.app.R.color.text_secondary)
        }
        binding.tvResultSeverity.setTextColor(severityColor)
    }
}
