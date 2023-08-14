package ru.tarlycheva.core.userLocation.data.client

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface ILocationClient {

    fun getLocationUpdates(interval: Long): Flow<Location>

    class LocationException(errorMessage: String) : Exception()
}