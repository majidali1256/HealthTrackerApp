package com.healthtracker.app.ui.profile

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.auth.FirebaseAuth
import com.healthtracker.app.data.local.dao.UserDao
import com.healthtracker.app.data.local.entities.User
import com.healthtracker.app.databinding.ActivityProfileSetupBinding
import com.healthtracker.app.ui.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Multi-step profile setup wizard.
 * Collects user's personal info, medical ID, and emergency contacts.
 */
@AndroidEntryPoint
class ProfileSetupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileSetupBinding
    private val viewModel: ProfileSetupViewModel by viewModels()
    
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    
    @Inject
    lateinit var userDao: UserDao
    
    private var selectedDateOfBirth: Date? = null
    private var selectedGender: String? = null
    
    private val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupClickListeners()
        observeViewModel()
        loadExistingData()
    }
    
    private fun setupUI() {
        // Set initial progress
        binding.progressIndicator.progress = 33
    }
    
    private fun loadExistingData() {
        lifecycleScope.launch {
            val userId = firebaseAuth.currentUser?.uid ?: return@launch
            val user = userDao.getUserById(userId)
            
            user?.let {
                binding.etFullName.setText(it.displayName)
                it.dateOfBirth?.let { dob ->
                    selectedDateOfBirth = dob
                    binding.etDateOfBirth.setText(dateFormatter.format(dob))
                }
                it.heightCm?.let { height ->
                    binding.etHeight.setText(height.toString())
                }
                it.weightKg?.let { weight ->
                    binding.etWeight.setText(weight.toString())
                }
                when (it.gender) {
                    "Male" -> binding.chipMale.isChecked = true
                    "Female" -> binding.chipFemale.isChecked = true
                    "Other" -> binding.chipOther.isChecked = true
                }
            }
        }
    }
    
    private fun setupClickListeners() {
        // Date of Birth picker
        binding.etDateOfBirth.setOnClickListener {
            showDatePicker()
        }
        binding.tilDateOfBirth.setEndIconOnClickListener {
            showDatePicker()
        }
        
        // Gender selection
        binding.chipGroupGender.setOnCheckedStateChangeListener { _, checkedIds ->
            selectedGender = when {
                checkedIds.contains(binding.chipMale.id) -> "Male"
                checkedIds.contains(binding.chipFemale.id) -> "Female"
                checkedIds.contains(binding.chipOther.id) -> "Other"
                else -> null
            }
        }
        
        // Skip button
        binding.btnSkip.setOnClickListener {
            navigateToDashboard()
        }
        
        // Next button
        binding.btnNext.setOnClickListener {
            saveProfileAndContinue()
        }
        
        // Profile picture
        binding.cardProfilePic.setOnClickListener {
            // TODO: Implement image picker
            Toast.makeText(this, "Image picker coming soon", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        selectedDateOfBirth?.let { calendar.time = it }
        
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                selectedDateOfBirth = calendar.time
                binding.etDateOfBirth.setText(dateFormatter.format(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.maxDate = System.currentTimeMillis()
        }.show()
    }
    
    private fun saveProfileAndContinue() {
        val fullName = binding.etFullName.text.toString().trim()
        val heightStr = binding.etHeight.text.toString().trim()
        val weightStr = binding.etWeight.text.toString().trim()
        
        // Validation
        if (fullName.isEmpty()) {
            binding.tilFullName.error = "Name is required"
            return
        }
        
        lifecycleScope.launch {
            val userId = firebaseAuth.currentUser?.uid ?: return@launch
            val existingUser = userDao.getUserById(userId)
            
            val updatedUser = existingUser?.copy(
                displayName = fullName,
                dateOfBirth = selectedDateOfBirth,
                gender = selectedGender,
                heightCm = heightStr.toFloatOrNull(),
                weightKg = weightStr.toFloatOrNull(),
                updatedAt = Date()
            ) ?: User(
                id = userId,
                email = firebaseAuth.currentUser?.email ?: "",
                displayName = fullName,
                dateOfBirth = selectedDateOfBirth,
                gender = selectedGender,
                heightCm = heightStr.toFloatOrNull(),
                weightKg = weightStr.toFloatOrNull()
            )
            
            userDao.insert(updatedUser)
            navigateToDashboard()
        }
    }
    
    private fun navigateToDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finishAffinity()
    }
    
    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    // Handle loading and error states
                }
            }
        }
    }
}
