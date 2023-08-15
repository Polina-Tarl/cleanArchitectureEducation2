package ru.tarlycheva.common.domain.repository

import retrofit2.Response
import ru.tarlycheva.common.domain.model.WeatherData

interface IWeatherDataRepository {

    suspend fun getWeatherData(city: String): Response<WeatherData>

}