package ru.tarlycheva.domain.repository

import retrofit2.Response
import ru.tarlycheva.domain.model.WeatherData

interface IWeatherDataRepository {

    suspend fun getWeatherData(city: String): Response<WeatherData>

}