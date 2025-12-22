package com.healthtracker.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.healthtracker.app.databinding.ActivitySignupBinding
import com.healthtracker.app.ui.dashboard.DashboardActivity
import com.healthtracker.app.ui.profile.ProfileSetupActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Sign up screen with email registration and social login options.
 * Includes password confirmation and terms acceptance.
 */
@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val viewModel: AuthViewModel by viewModels()
    
    @Inject
    lateinit var googleSignInClient: GoogleSignInClient
    
    // Google Sign In launcher
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            viewModel.signInWithGoogle(account)
        } catch (e: ApiException) {
            Log.e("SignUpActivity", "Google sign in failed", e)
            Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivitySignupBinding.inflate(layoutInflater)
            setContentView(binding.root)
            
            setupClickListeners()
            observeViewModel()
        } catch (e: Exception) {
            Log.e("SignUpActivity", "Error in onCreate", e)
            Toast.makeText(this, "Error loading sign up screen", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun setupClickListeners() {
        // Back Button
        binding.btnBack.setOnClickListener {
            finish()
        }
        
        // Email Sign Up
        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()
            viewModel.signUpWithEmail(email, password, confirmPassword)
        }
        
        // Google Sign Up
        binding.btnGoogle.setOnClickListener {
            try {
                val signInIntent = googleSignInClient.signInIntent
                googleSignInLauncher.launch(signInIntent)
            } catch (e: Exception) {
                Log.e("SignUpActivity", "Google sign in error", e)
                Toast.makeText(this, "Google Sign-In not available", Toast.LENGTH_SHORT).show()
            }
        }
        
        // Facebook Sign Up - Disabled for now
        binding.btnFacebook.setOnClickListener {
            Toast.makeText(this, "Facebook login coming soon", Toast.LENGTH_SHORT).show()
        }
        
        // Navigate to Login
        binding.tvLogin.setOnClickListener {
            finish()
        }
    }
    
    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Observe UI State
                launch {
                    viewModel.uiState.collect { state ->
                        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                        binding.btnSignUp.isEnabled = !state.isLoading
                        binding.btnGoogle.isEnabled = !state.isLoading
                        binding.btnFacebook.isEnabled = !state.isLoading
                        
                        binding.tilEmail.error = state.emailError
                        binding.tilPassword.error = state.passwordError
                        binding.tilConfirmPassword.error = state.confirmPasswordError
                        
                        state.error?.let { error ->
                            Toast.makeText(this@SignUpActivity, error, Toast.LENGTH_SHORT).show()
                            viewModel.clearError()
                        }
                    }
                }
                
                // Observe Events
                launch {
                    viewModel.events.collect { event ->
                        when (event) {
                            is AuthEvent.NavigateToDashboard -> {
                                startActivity(Intent(this@SignUpActivity, DashboardActivity::class.java))
                                finishAffinity()
                            }
                            is AuthEvent.NavigateToProfileSetup -> {
                                startActivity(Intent(this@SignUpActivity, ProfileSetupActivity::class.java))
                                finishAffinity()
                            }
                            is AuthEvent.ShowMessage -> {
                                Toast.makeText(this@SignUpActivity, event.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
}
