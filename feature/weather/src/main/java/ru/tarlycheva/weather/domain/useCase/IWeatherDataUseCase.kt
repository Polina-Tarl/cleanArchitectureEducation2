package ru.tarlycheva.weather.domain.useCase

import retrofit2.Response
import ru.tarlycheva.weather.domain.model.WeatherData

interface IWeatherDataUseCase {

    suspend fun getWeatherData(city: String): Response<WeatherData>

}