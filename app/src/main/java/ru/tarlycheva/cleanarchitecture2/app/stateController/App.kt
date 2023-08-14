package ru.tarlycheva.cleanarchitecture2.app.stateController

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import ru.tarlycheva.core.userLocation.domain.service.LocationService.Companion.NOTIFICATION_CHANNEL_ID

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initLocationService()
    }

    private fun initLocationService() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "Location",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }

}