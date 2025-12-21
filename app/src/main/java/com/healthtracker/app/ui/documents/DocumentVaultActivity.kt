package com.healthtracker.app.ui.documents

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.healthtracker.app.databinding.ActivityDocumentVaultBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Encrypted Document Vault for medical records, lab results, and prescriptions.
 * Supports viewing, uploading, and cloud sync of documents.
 */
@AndroidEntryPoint
class DocumentVaultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDocumentVaultBinding
    private val viewModel: DocumentVaultViewModel by viewModels()
    private lateinit var documentAdapter: DocumentAdapter
    
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private val pickDocument = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.uploadDocument(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentVaultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
        
        firebaseAuth.currentUser?.uid?.let { userId ->
            viewModel.loadDocuments(userId)
        }
    }

    private fun setupToolbar() {
        binding.btnBack.setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        documentAdapter = DocumentAdapter(
            onDocumentClick = { document -> 
                viewModel.openDocument(document)
            },
            onDeleteClick = { document -> 
                viewModel.deleteDocument(document)
            }
        )
        
        binding.rvDocuments.apply {
            layoutManager = LinearLayoutManager(this@DocumentVaultActivity)
            adapter = documentAdapter
        }
    }

    private fun setupClickListeners() {
        binding.fabAddDocument.setOnClickListener {
            pickDocument.launch("*/*")
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.documents.collect { documents ->
                        documentAdapter.submitList(documents)
                        binding.emptyState.visibility = if (documents.isEmpty()) View.VISIBLE else View.GONE
                        binding.rvDocuments.visibility = if (documents.isEmpty()) View.GONE else View.VISIBLE
                    }
                }
                
                launch {
                    viewModel.uiState.collect { state ->
                        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                        
                        state.error?.let { error ->
                            Toast.makeText(this@DocumentVaultActivity, error, Toast.LENGTH_SHORT).show()
                            viewModel.clearError()
                        }
                    }
                }
            }
        }
    }
}
