package ru.tarlycheva.weather.domain.useCase

import retrofit2.Response
import ru.tarlycheva.weather.domain.model.WeatherData
import ru.tarlycheva.weather.domain.repository.IWeatherDataRepository
import javax.inject.Inject

class WeatherDataUseCase @Inject constructor(
    private val weatherDataRepository: IWeatherDataRepository
) {

    suspend fun getWeatherData(city: String): Response<WeatherData> {
        return weatherDataRepository.getWeatherData(city)
    }

}