package ru.tarlycheva.domain.useCase

import retrofit2.Response
import ru.tarlycheva.domain.model.WeatherData

interface IWeatherDataUseCase {

    suspend fun getWeatherData(city: String): Response<WeatherData>
}