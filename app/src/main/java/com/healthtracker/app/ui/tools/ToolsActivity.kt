package com.healthtracker.app.ui.tools

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.healthtracker.app.R
import com.healthtracker.app.databinding.ActivityToolsBinding
import com.healthtracker.app.services.EmergencySOSService
import com.healthtracker.app.ui.dashboard.DashboardActivity
import com.healthtracker.app.ui.documents.DocumentVaultActivity
import com.healthtracker.app.ui.food.FoodLoggerActivity
import com.healthtracker.app.ui.medications.MedicationsActivity
import com.healthtracker.app.ui.symptoms.SymptomCheckerActivity
import com.healthtracker.app.ui.trends.TrendsActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Tools & Safety Hub screen.
 * Provides access to professional features and emergency services.
 */
@AndroidEntryPoint
class ToolsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityToolsBinding
    
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    companion object {
        private const val RC_LOCATION_PERMISSION = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToolsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupClickListeners()
        setupBottomNavigation()
        applyLiftAnimations()
    }

    private fun setupClickListeners() {
        // Emergency SOS
        binding.cardEmergency.setOnClickListener {
            checkLocationPermissionAndStartSOS()
        }
        
        // Medical Records / Document Vault
        binding.cardMedicalRecords.setOnClickListener {
            startActivity(Intent(this, DocumentVaultActivity::class.java))
        }
        
        // Symptom Checker
        binding.cardSymptomChecker.setOnClickListener {
            startActivity(Intent(this, SymptomCheckerActivity::class.java))
        }
        
        // Food Logger
        binding.cardFoodLogger.setOnClickListener {
            startActivity(Intent(this, FoodLoggerActivity::class.java))
        }
        
        // Medications
        binding.cardMedications.setOnClickListener {
            startActivity(Intent(this, MedicationsActivity::class.java))
        }
        
        // Trends
        binding.cardTrends.setOnClickListener {
            startActivity(Intent(this, TrendsActivity::class.java))
        }
        
        // Export Reports
        binding.cardExportReports.setOnClickListener {
            Toast.makeText(this, "Generating PDF report...", Toast.LENGTH_SHORT).show()
            // TODO: Implement PDF export
        }
        
        // FAB
        binding.fabAddLog.setOnClickListener {
            // Show quick log bottom sheet
            showQuickLogSheet()
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNav.selectedItemId = R.id.nav_tools
        
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    true
                }
                R.id.nav_trends -> {
                    startActivity(Intent(this, TrendsActivity::class.java))
                    true
                }
                R.id.nav_tools -> true // Already here
                R.id.nav_profile -> {
                    // Navigate to profile
                    true
                }
                else -> false
            }
        }
    }

    private fun applyLiftAnimations() {
        val cards = listOf(
            binding.cardEmergency,
            binding.cardMedicalRecords,
            binding.cardSymptomChecker,
            binding.cardFoodLogger,
            binding.cardMedications,
            binding.cardTrends,
            binding.cardExportReports
        )
        
        cards.forEach { card ->
            card.setOnTouchListener { v, event ->
                when (event.action) {
                    android.view.MotionEvent.ACTION_DOWN -> {
                        v.animate()
                            .scaleX(1.03f)
                            .scaleY(1.03f)
                            .translationZ(8f)
                            .setDuration(100)
                            .start()
                    }
                    android.view.MotionEvent.ACTION_UP,
                    android.view.MotionEvent.ACTION_CANCEL -> {
                        v.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .translationZ(0f)
                            .setDuration(100)
                            .start()
                        v.performClick()
                    }
                }
                true
            }
        }
    }

    private fun checkLocationPermissionAndStartSOS() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                RC_LOCATION_PERMISSION
            )
        } else {
            startEmergencySOS()
        }
    }

    private fun startEmergencySOS() {
        EmergencySOSService.startService(this)
        Toast.makeText(
            this,
            "Emergency SOS activated! Sharing location with emergency contacts.",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RC_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startEmergencySOS()
            } else {
                Toast.makeText(
                    this,
                    "Location permission required for Emergency SOS",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showQuickLogSheet() {
        // TODO: Show BottomSheetDialogFragment for quick logging
        Toast.makeText(this, "Quick log menu", Toast.LENGTH_SHORT).show()
    }
}
