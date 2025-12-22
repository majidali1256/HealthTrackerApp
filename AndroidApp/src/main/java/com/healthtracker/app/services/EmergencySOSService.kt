package com.healthtracker.app.services

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.telephony.SmsManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import com.healthtracker.app.data.local.dao.UserDao
import com.healthtracker.app.ui.dashboard.DashboardActivity
import com.healthtracker.app.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

/**
 * Emergency SOS Service.
 * Shares live GPS location with emergency contacts when activated.
 * Sends SMS with location link and medical ID information.
 */
@AndroidEntryPoint
class EmergencySOSService : Service() {

    @Inject
    lateinit var userDao: UserDao
    
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    private var isActive = false

    companion object {
        private const val NOTIFICATION_ID = 2001
        private const val CHANNEL_ID = Constants.HEALTH_ALERTS_CHANNEL_ID
        private const val LOCATION_UPDATE_INTERVAL = 30_000L // 30 seconds
        
        fun startService(context: Context) {
            val intent = Intent(context, EmergencySOSService::class.java)
            context.startForegroundService(intent)
        }
        
        fun stopService(context: Context) {
            val intent = Intent(context, EmergencySOSService::class.java)
            context.stopService(intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createNotificationChannel()
        setupLocationCallback()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isActive) {
            isActive = true
            startForeground(NOTIFICATION_ID, createNotification("Activating Emergency SOS..."))
            startLocationTracking()
            sendInitialAlert()
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        isActive = false
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun setupLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    sendLocationUpdate(location)
                    updateNotification(location)
                }
            }
        }
    }

    private fun startLocationTracking() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            stopSelf()
            return
        }

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            LOCATION_UPDATE_INTERVAL
        ).apply {
            setMinUpdateIntervalMillis(LOCATION_UPDATE_INTERVAL / 2)
            setWaitForAccurateLocation(false)
        }.build()

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun sendInitialAlert() {
        serviceScope.launch {
            val userId = firebaseAuth.currentUser?.uid ?: return@launch
            val user = userDao.getUserById(userId) ?: return@launch
            
            val contacts = user.emergencyContacts ?: return@launch
            
            // Parse emergency contacts (format: "Name:Phone,Name:Phone")
            val contactList = contacts.split(",").mapNotNull { contact ->
                val parts = contact.split(":")
                if (parts.size == 2) parts[1].trim() else null
            }
            
            val message = buildString {
                append("🚨 EMERGENCY ALERT 🚨\n\n")
                append("${user.displayName} has activated Emergency SOS.\n\n")
                
                user.medicalId?.let { id -> 
                    append("Medical ID: $id\n") 
                }
                
                user.bloodType?.let { blood -> 
                    append("Blood Type: $blood\n") 
                }
                append("\nLive location updates will follow.")
            }
            
            contactList.forEach { phoneNumber ->
                sendSMS(phoneNumber, message)
            }
        }
    }

    private fun sendLocationUpdate(location: Location) {
        serviceScope.launch {
            val userId = firebaseAuth.currentUser?.uid ?: return@launch
            val user = userDao.getUserById(userId) ?: return@launch
            
            val contacts = user.emergencyContacts ?: return@launch
            val contactList = contacts.split(",").mapNotNull { contact ->
                val parts = contact.split(":")
                if (parts.size == 2) parts[1].trim() else null
            }
            
            val googleMapsUrl = "https://maps.google.com/?q=${location.latitude},${location.longitude}"
            val message = "📍 Location Update: $googleMapsUrl"
            
            contactList.forEach { phoneNumber ->
                sendSMS(phoneNumber, message)
            }
        }
    }

    private fun sendSMS(phoneNumber: String, message: String) {
        try {
            val smsManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                getSystemService(SmsManager::class.java)
            } else {
                @Suppress("DEPRECATION")
                SmsManager.getDefault()
            }
            
            smsManager?.sendTextMessage(
                phoneNumber,
                null,
                message,
                null,
                null
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Emergency Alerts",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Emergency SOS notifications"
            enableVibration(true)
            setShowBadge(true)
        }
        
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(content: String): Notification {
        val stopIntent = Intent(this, EmergencySOSService::class.java).apply {
            action = "STOP_SOS"
        }
        val stopPendingIntent = PendingIntent.getService(
            this,
            0,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val openIntent = Intent(this, DashboardActivity::class.java)
        val openPendingIntent = PendingIntent.getActivity(
            this,
            0,
            openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("🚨 Emergency SOS Active")
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setOngoing(true)
            .setColor(0xFFE53935.toInt())
            .setContentIntent(openPendingIntent)
            .addAction(android.R.drawable.ic_menu_close_clear_cancel, "Stop SOS", stopPendingIntent)
            .build()
    }

    private fun updateNotification(location: Location) {
        val content = "Sharing location: ${String.format(Locale.US, "%.4f", location.latitude)}, ${String.format(Locale.US, "%.4f", location.longitude)}"
        val notification = createNotification(content)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}
