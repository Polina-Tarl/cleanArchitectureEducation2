package ru.tarlycheva.core.userLocation.domain.client

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import ru.tarlycheva.core.userLocation.data.client.ILocationClient
import ru.tarlycheva.core.utils.hasLocationPermission

class LocationClient(
    private val context: Context,
    private val client: FusedLocationProviderClient
) : ILocationClient {

    @SuppressLint("MissingPermission")
    override fun getLocationUpdates(interval: Long): Flow<Location> {
        return callbackFlow {
            if (context.hasLocationPermission()) {

                val locationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val isNetworkEnable =
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                val isGpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                if (!isNetworkEnable && !isGpsEnable)
                    ILocationClient.LocationException("Network and gps is not enable")

                val request = LocationRequest.Builder(interval).build()
                val locationCallback = object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult) {
                        super.onLocationResult(result)

                        result.locations.lastOrNull()?.let { location ->
                            launch { send(location) }
                        }
                    }
                }


                client.requestLocationUpdates(
                    request,
                    locationCallback,
                    Looper.getMainLooper()
                )

                awaitClose {
                    client.removeLocationUpdates(locationCallback)
                }

            } else {
                ILocationClient.LocationException("Missing location permission")
            }
        }
    }
}