package ru.tarlycheva.userweather.presentation

import android.location.Address
import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import ru.tarlycheva.core.userLocation.domain.repository.LocationRepository
import javax.inject.Inject

@HiltViewModel
class UserWeatherViewModel @Inject constructor(

) : ViewModel() {

    private val _loading: MutableLiveData<Pair<Boolean, Int>> =
        MutableLiveData<Pair<Boolean, Int>>().apply { value = Pair(true, 0) }
    val loading: LiveData<Pair<Boolean, Int>> get() = _loading

    private val _cityName: MutableLiveData<String?> =
        MutableLiveData<String?>().apply { value = null }
    val cityName: LiveData<String?> get() = _cityName

    private val _countryName: MutableLiveData<String?> =
        MutableLiveData<String?>().apply { value = null }
    val countryName: LiveData<String?> get() = _countryName


    fun setAddress(address: Address) {
        _cityName.postValue(address.locality)
        _countryName.postValue(address.countryName)
        _loading.postValue(Pair(false, 0))
    }


    private fun getWeatherForCity() {


    }


}