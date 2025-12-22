package com.healthtracker.app.ui.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.healthtracker.app.R
import com.healthtracker.app.databinding.ActivityProfileBinding
import com.healthtracker.app.ui.auth.LoginActivity
import com.healthtracker.app.ui.dashboard.DashboardActivity
import com.healthtracker.app.ui.tools.ToolsActivity
import com.healthtracker.app.ui.trends.TrendsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupClickListeners()
        setupBottomNav()
    }

    private fun setupUI() {
        // Load user data
        val user = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
        binding.tvUserName.text = user?.displayName ?: "John Doe"
        binding.tvUserEmail.text = user?.email ?: "john.doe@email.com"
        binding.tvHeartRateStat.text = "72"
        binding.tvStepsStat.text = "8.2K"
        binding.tvSleepStat.text = "7.5h"
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.rowEditProfile.setOnClickListener {
            startActivity(Intent(this, ProfileSetupActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.rowMedicalID.setOnClickListener {
            startActivity(Intent(this, MedicalIdActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.rowEmergency.setOnClickListener {
            startActivity(Intent(this, MedicalIdActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.rowNotifications.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.rowPrivacy.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.rowHelp.setOnClickListener {
            android.widget.Toast.makeText(this, "Contact: support@healthtracker.com", android.widget.Toast.LENGTH_LONG).show()
        }

        binding.btnLogout.setOnClickListener {
            com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun setupBottomNav() {
        binding.bottomNav.selectedItemId = R.id.nav_profile

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    overridePendingTransition(R.anim.nav_default_enter, R.anim.nav_default_exit)
                    true
                }
                R.id.nav_trends -> {
                    startActivity(Intent(this, TrendsActivity::class.java))
                    overridePendingTransition(R.anim.nav_default_enter, R.anim.nav_default_exit)
                    true
                }
                R.id.nav_tools -> {
                    startActivity(Intent(this, ToolsActivity::class.java))
                    overridePendingTransition(R.anim.nav_default_enter, R.anim.nav_default_exit)
                    true
                }
                R.id.nav_profile -> true // Already here
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
