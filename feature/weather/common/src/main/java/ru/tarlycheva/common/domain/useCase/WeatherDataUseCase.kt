package ru.tarlycheva.common.domain.useCase

import retrofit2.Response
import ru.tarlycheva.common.domain.model.WeatherData
import ru.tarlycheva.common.domain.repository.IWeatherDataRepository
import javax.inject.Inject

class WeatherDataUseCase @Inject constructor(
    private val weatherDataRepository: IWeatherDataRepository
) {

    suspend fun getWeatherData(city: String): Response<WeatherData> {
        return weatherDataRepository.getWeatherData(city)
    }

}