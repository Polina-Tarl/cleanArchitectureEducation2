package ru.tarlycheva.weather.domain.repository

import retrofit2.Response
import ru.tarlycheva.weather.domain.model.WeatherData

interface IWeatherDataRepository {

    suspend fun getWeatherData(city: String): Response<WeatherData>

}