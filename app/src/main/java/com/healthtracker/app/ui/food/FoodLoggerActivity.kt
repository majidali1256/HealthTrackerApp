package com.healthtracker.app.ui.food

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.auth.FirebaseAuth
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.healthtracker.app.databinding.ActivityFoodLoggerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

/**
 * Food Logger with barcode scanning using ML Kit.
 * Supports manual entry and nutritional database lookup.
 */
@AndroidEntryPoint
class FoodLoggerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodLoggerBinding
    private val viewModel: FoodLoggerViewModel by viewModels()
    
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    
    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null

    private val requestCameraPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startCamera()
        } else {
            Toast.makeText(this, "Camera permission required for scanning", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodLoggerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        cameraExecutor = Executors.newSingleThreadExecutor()
        
        setupClickListeners()
        observeViewModel()
        
        firebaseAuth.currentUser?.uid?.let { userId ->
            viewModel.loadTodayNutrition(userId)
        }
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener { finish() }
        
        binding.btnScanBarcode.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ) {
                toggleCameraView(true)
                startCamera()
            } else {
                requestCameraPermission.launch(Manifest.permission.CAMERA)
            }
        }
        
        binding.btnCapture.setOnClickListener {
            captureAndAnalyze()
        }
        
        binding.btnCloseCamera.setOnClickListener {
            toggleCameraView(false)
        }
        
        binding.btnAddManually.setOnClickListener {
            showManualEntryDialog()
        }
        
        binding.btnLogWater.setOnClickListener {
            viewModel.logWater()
            Toast.makeText(this, "Water logged!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toggleCameraView(show: Boolean) {
        binding.cameraContainer.visibility = if (show) View.VISIBLE else View.GONE
        binding.mainContent.visibility = if (show) View.GONE else View.VISIBLE
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
            }
            
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()
            
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                Toast.makeText(this, "Camera error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    private fun captureAndAnalyze() {
        val imageCapture = imageCapture ?: return
        
        binding.progressScanning.visibility = View.VISIBLE
        
        imageCapture.takePicture(
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(imageProxy: ImageProxy) {
                    val mediaImage = imageProxy.image
                    if (mediaImage != null) {
                        val image = InputImage.fromMediaImage(
                            mediaImage,
                            imageProxy.imageInfo.rotationDegrees
                        )
                        
                        val scanner = BarcodeScanning.getClient()
                        scanner.process(image)
                            .addOnSuccessListener { barcodes ->
                                binding.progressScanning.visibility = View.GONE
                                
                                if (barcodes.isNotEmpty()) {
                                    val barcode = barcodes[0].rawValue
                                    viewModel.lookupFood(barcode ?: "")
                                    toggleCameraView(false)
                                } else {
                                    Toast.makeText(
                                        this@FoodLoggerActivity,
                                        "No barcode found. Try again.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            .addOnFailureListener {
                                binding.progressScanning.visibility = View.GONE
                                Toast.makeText(
                                    this@FoodLoggerActivity,
                                    "Scan failed: ${it.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnCompleteListener {
                                imageProxy.close()
                            }
                    }
                }
                
                override fun onError(exception: ImageCaptureException) {
                    binding.progressScanning.visibility = View.GONE
                    Toast.makeText(
                        this@FoodLoggerActivity,
                        "Capture failed: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    // Update nutrition summary
                    binding.tvCaloriesValue.text = state.totalCalories.toString()
                    binding.progressCalories.progress = 
                        ((state.totalCalories.toFloat() / state.caloriesGoal) * 100).toInt()
                    
                    binding.tvProteinValue.text = "${state.protein}g"
                    binding.tvCarbsValue.text = "${state.carbs}g"
                    binding.tvFatValue.text = "${state.fat}g"
                    binding.tvWaterValue.text = "${state.waterGlasses} glasses"
                }
            }
        }
    }

    private fun showManualEntryDialog() {
        // TODO: Show dialog for manual food entry
        Toast.makeText(this, "Manual entry dialog", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
