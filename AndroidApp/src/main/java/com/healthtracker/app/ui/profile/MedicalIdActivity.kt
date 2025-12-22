package com.healthtracker.app.ui.profile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.healthtracker.app.R
import com.healthtracker.app.databinding.ActivityMedicalIdBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicalIdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMedicalIdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicalIdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        loadSavedData()
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        binding.btnSave.setOnClickListener {
            saveData()
            Toast.makeText(this, "Medical ID saved", Toast.LENGTH_SHORT).show()
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    private fun loadSavedData() {
        val prefs = getSharedPreferences("medical_id", MODE_PRIVATE)
        binding.etBloodType.setText(prefs.getString("blood_type", ""))
        binding.etAllergies.setText(prefs.getString("allergies", ""))
        binding.etMedicalConditions.setText(prefs.getString("conditions", ""))
        binding.etMedications.setText(prefs.getString("medications", ""))
        binding.etEmergencyContact.setText(prefs.getString("emergency_name", ""))
        binding.etEmergencyPhone.setText(prefs.getString("emergency_phone", ""))
        binding.etDoctorName.setText(prefs.getString("doctor_name", ""))
        binding.etDoctorPhone.setText(prefs.getString("doctor_phone", ""))
        binding.etNotes.setText(prefs.getString("notes", ""))
    }

    private fun saveData() {
        val prefs = getSharedPreferences("medical_id", MODE_PRIVATE)
        prefs.edit().apply {
            putString("blood_type", binding.etBloodType.text.toString())
            putString("allergies", binding.etAllergies.text.toString())
            putString("conditions", binding.etMedicalConditions.text.toString())
            putString("medications", binding.etMedications.text.toString())
            putString("emergency_name", binding.etEmergencyContact.text.toString())
            putString("emergency_phone", binding.etEmergencyPhone.text.toString())
            putString("doctor_name", binding.etDoctorName.text.toString())
            putString("doctor_phone", binding.etDoctorPhone.text.toString())
            putString("notes", binding.etNotes.text.toString())
            apply()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
