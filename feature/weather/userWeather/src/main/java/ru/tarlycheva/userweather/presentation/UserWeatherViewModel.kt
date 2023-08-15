package ru.tarlycheva.userweather.presentation

import android.location.Address
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.tarlycheva.domain.model.WeatherData
import ru.tarlycheva.domain.useCase.IWeatherDataUseCase
import javax.inject.Inject

@HiltViewModel
class UserWeatherViewModel @Inject constructor(
    private val weatherDataUseCase: IWeatherDataUseCase
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

    private val _weatherData: MutableLiveData<WeatherData?> =
        MutableLiveData<WeatherData?>().apply { value = null }
    val weatherData: LiveData<WeatherData?> get() = _weatherData


    init {
        initLoading()
    }


    fun setAddress(address: Address) {
        _cityName.postValue(address.locality)
        _countryName.postValue(address.countryName)
        getWeatherData(address.locality)
    }


    private fun getWeatherData(city: String) {
        viewModelScope.launch {
            try {
                val response = weatherDataUseCase.getWeatherData(city).body()
                _weatherData.value = response
            } catch (e: Exception) {
                Log.e("UserWeatherViewModel", "$e")
            }
        }
    }

    private fun initLoading() {
        viewModelScope.launch {
            (0..3).forEach {
                delay(200L)
                when (it) {
                    0 -> _loading.value = Pair(true, 0)
                    1 -> _loading.value = Pair(true, 1)
                    2 -> _loading.value = Pair(true, 2)
                    3 -> {
                        if (_weatherData.value != null)
                            _loading.value = Pair(false, 0)
                        else {
                            _loading.value = Pair(true, 3)
                            initLoading()
                        }
                    }
                }
            }
        }
    }


}