package ru.tarlycheva.core.userLocation.domain.repository

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tarlycheva.core.userLocation.data.client.ILocationClient
import ru.tarlycheva.core.userLocation.domain.client.LocationClient
import java.util.*
import javax.inject.Inject

class LocationRepository constructor(
    private val applicationContext: Context
) {
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: ILocationClient
    private lateinit var geocoder: Geocoder

    init {
        initLateInitClasses()
    }

    private fun initLateInitClasses() {
        locationClient = LocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )

        geocoder = Geocoder(applicationContext, Locale.getDefault())

    }

    fun getLocation(successCallback: (Address) -> Unit, errorCallback: () -> Unit) {
        locationClient
            .getLocationUpdates(50000L)
            .catch { e ->
                Log.e(this::class.java.simpleName, "${e.message}")
            }
            .onEach { location ->
                setAddress(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    successCallback = successCallback,
                    errorCallback = errorCallback
                )

            }
            .launchIn(serviceScope)
    }

    private fun setAddress(
        latitude: Double,
        longitude: Double,
        successCallback: (Address) -> Unit,
        errorCallback: () -> Unit
    ) {
        geocoder.getFromLocation(
            latitude,
            longitude,
            1,
            object : Geocoder.GeocodeListener {
                override fun onGeocode(addresses: MutableList<Address>) {
                    val address = addresses.firstOrNull()

                    address?.let {
                        successCallback.invoke(it)
                    } ?: run {
                        errorCallback.invoke()
                    }
                }

                override fun onError(errorMessage: String?) {
                    super.onError(errorMessage)
                    Log.e(this::class.java.simpleName, "$errorMessage")
                    errorCallback.invoke()
                }
            })

    }

    fun stop() {
        serviceScope.cancel()
    }

}