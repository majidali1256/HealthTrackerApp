package com.healthtracker.app.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.healthtracker.app.data.local.dao.UserDao
import com.healthtracker.app.ui.auth.LoginActivity
import com.healthtracker.app.ui.dashboard.DashboardActivity
import com.healthtracker.app.ui.profile.ProfileSetupActivity
import dagger.hilt.android.AndroidEntryPoint
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
    
    @Inject
    lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        
        // Keep splash screen until we determine destination
        var keepSplash = true
        splashScreen.setKeepOnScreenCondition { keepSplash }
        
        lifecycleScope.launch {
            val destination = determineDestination()
            keepSplash = false
            navigateTo(destination)
        }
    }
    
    private suspend fun determineDestination(): Class<*> {
        val currentUser = firebaseAuth.currentUser
        
        return when {
            // Not logged in -> Login
            currentUser == null -> LoginActivity::class.java
            
            // Logged in, check if profile is complete
            else -> {
                val localUser = userDao.getUserById(currentUser.uid)
                if (localUser?.dateOfBirth != null && localUser.heightCm != null) {
                    DashboardActivity::class.java
                } else {
                    ProfileSetupActivity::class.java
                }
            }
        }
    }
    
    private fun navigateTo(destination: Class<*>) {
        startActivity(Intent(this, destination))
        finish()
    }
}
