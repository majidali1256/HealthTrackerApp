package com.healthtracker.app.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.healthtracker.app.ui.auth.LoginActivity
import com.healthtracker.app.ui.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Splash screen activity that checks authentication state
 * and navigates to the appropriate screen.
 */
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        
        // Keep splash screen until we determine destination
        var keepSplash = true
        splashScreen.setKeepOnScreenCondition { keepSplash }
        
        lifecycleScope.launch {
            try {
                // Small delay for splash screen animation
                delay(500)
                
                val destination = determineDestination()
                keepSplash = false
                navigateTo(destination)
            } catch (e: Exception) {
                Log.e("SplashActivity", "Error determining destination", e)
                keepSplash = false
                // On any error, just go to Dashboard if logged in, else Login
                val fallback = if (firebaseAuth.currentUser != null) {
                    DashboardActivity::class.java
                } else {
                    LoginActivity::class.java
                }
                navigateTo(fallback)
            }
        }
    }
    
    private fun determineDestination(): Class<*> {
        val currentUser = firebaseAuth.currentUser
        
        return if (currentUser == null) {
            // Not logged in -> Login
            LoginActivity::class.java
        } else {
            // Logged in -> Go straight to Dashboard
            // Skip profile check to avoid database issues
            DashboardActivity::class.java
        }
    }
    
    private fun navigateTo(destination: Class<*>) {
        startActivity(Intent(this, destination))
        finish()
    }
}
