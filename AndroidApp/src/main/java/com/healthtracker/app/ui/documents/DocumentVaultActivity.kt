package com.healthtracker.app.ui.documents

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.healthtracker.app.R
import com.healthtracker.app.databinding.ActivityDocumentVaultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DocumentVaultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDocumentVaultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentVaultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        binding.fabAddDocument.setOnClickListener {
            Toast.makeText(this, "Add document", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
