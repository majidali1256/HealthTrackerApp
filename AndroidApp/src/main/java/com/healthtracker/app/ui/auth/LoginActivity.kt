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
import com.healthtracker.app.databinding.ActivityLoginBinding
import com.healthtracker.app.ui.dashboard.DashboardActivity
import com.healthtracker.app.ui.profile.ProfileSetupActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Login screen with email/password and social login options.
 * Follows minimalist aesthetic with stacked social buttons.
 */
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
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
            Log.e("LoginActivity", "Google sign in failed", e)
            Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)
            
            setupClickListeners()
            observeViewModel()
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error in onCreate", e)
            Toast.makeText(this, "Error loading login screen", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun setupClickListeners() {
        // Email Login
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            viewModel.signInWithEmail(email, password)
        }
        
        // Google Sign In
        binding.btnGoogle.setOnClickListener {
            try {
                val signInIntent = googleSignInClient.signInIntent
                googleSignInLauncher.launch(signInIntent)
            } catch (e: Exception) {
                Log.e("LoginActivity", "Google sign in error", e)
                Toast.makeText(this, "Google Sign-In not available", Toast.LENGTH_SHORT).show()
            }
        }
        
        // Facebook Sign In - Disabled for now
        binding.btnFacebook.setOnClickListener {
            Toast.makeText(this, "Facebook login coming soon", Toast.LENGTH_SHORT).show()
        }
        
        // Forgot Password
        binding.tvForgotPassword.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            if (email.isNotEmpty()) {
                viewModel.sendPasswordReset(email)
            } else {
                binding.tilEmail.error = "Enter your email first"
            }
        }
        
        // Navigate to Sign Up
        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
    
    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Observe UI State
                launch {
                    viewModel.uiState.collect { state ->
                        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                        binding.btnLogin.isEnabled = !state.isLoading
                        binding.btnGoogle.isEnabled = !state.isLoading
                        binding.btnFacebook.isEnabled = !state.isLoading
                        
                        binding.tilEmail.error = state.emailError
                        binding.tilPassword.error = state.passwordError
                        
                        state.error?.let { error ->
                            Toast.makeText(this@LoginActivity, error, Toast.LENGTH_SHORT).show()
                            viewModel.clearError()
                        }
                    }
                }
                
                // Observe Events
                launch {
                    viewModel.events.collect { event ->
                        when (event) {
                            is AuthEvent.NavigateToDashboard -> {
                                startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                                finish()
                            }
                            is AuthEvent.NavigateToProfileSetup -> {
                                startActivity(Intent(this@LoginActivity, ProfileSetupActivity::class.java))
                                finish()
                            }
                            is AuthEvent.ShowMessage -> {
                                Toast.makeText(this@LoginActivity, event.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
}
