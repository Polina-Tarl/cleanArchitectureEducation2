package ru.tarlycheva.sochi.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.tarlycheva.common.domain.model.WeatherData
import ru.tarlycheva.common.domain.useCase.WeatherDataUseCase
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherDataUseCase: WeatherDataUseCase
) : ViewModel() {

    private val _loading: MutableLiveData<Pair<Boolean, Int>> =
        MutableLiveData<Pair<Boolean, Int>>().apply { value = Pair(true, 0) }
    val loading: LiveData<Pair<Boolean, Int>> get() = _loading

    private val _weatherData: MutableLiveData<WeatherData?> =
        MutableLiveData<WeatherData?>().apply { value = null }
    val weatherData: LiveData<WeatherData?> get() = _weatherData


    init {
        initLoading()
        getWeatherData(CITY_DEFAULT)
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

    private fun getWeatherData(city: String) {
       viewModelScope.launch {
           try {
               val response = weatherDataUseCase.getWeatherData(city).body()
               _weatherData.value = response
           } catch (e: Exception) {
                Log.e("WeatherViewModel", "$e")
           }
       }
    }


    companion object {
        private const val CITY_DEFAULT = "Sochi"
    }

}