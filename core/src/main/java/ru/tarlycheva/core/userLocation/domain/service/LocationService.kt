package ru.tarlycheva.core.userLocation.domain.service

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tarlycheva.core.R
import ru.tarlycheva.core.userLocation.data.client.ILocationClient
import ru.tarlycheva.core.userLocation.domain.client.LocationClient
import ru.tarlycheva.core.userLocation.domain.repository.LocationRepository
import java.util.*

@AndroidEntryPoint
class LocationService : Service() {

    private lateinit var notificationManager: NotificationManager
    private lateinit var locationRepository: LocationRepository


    override fun onCreate() {
        super.onCreate()

        locationRepository = LocationRepository(applicationContext)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getString(R.string.notification_Location_title))
            .setContentText(getString(R.string.notification_Location_text, NO_CITY))
            .setSmallIcon(R.drawable.ic_brightness)
            .setOngoing(true)

        locationRepository.getLocation(
            successCallback = { address ->
                updateNotification(address.locality, notification)
            },
            errorCallback = { }
        )

        startForeground(
            NOTIFICATION_FOREGROUND_ID,
            notification.build()
        )
    }

    private fun stop() {
        locationRepository.stop()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun updateNotification(city: String?, notification: NotificationCompat.Builder) {
        val updateNotification = notification.setContentText(
            getString(R.string.notification_Location_text, city ?: NO_CITY)
        )

        notificationManager.notify(NOTIFICATION_FOREGROUND_ID, updateNotification.build())
    }

    override fun onDestroy() {
        super.onDestroy()
        locationRepository.stop()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"

        const val NOTIFICATION_CHANNEL_ID = "4353_nci"
        const val NOTIFICATION_FOREGROUND_ID = 76342

        private const val NO_CITY = "..."
    }
}