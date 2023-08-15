package ru.tarlycheva.data.useCase

import retrofit2.Response
import ru.tarlycheva.domain.model.WeatherData
import ru.tarlycheva.domain.repository.IWeatherDataRepository
import ru.tarlycheva.domain.useCase.IWeatherDataUseCase
import javax.inject.Inject


class WeatherDataUseCase @Inject constructor(
    private val weatherDataRepository: IWeatherDataRepository
) : IWeatherDataUseCase {

    override suspend fun getWeatherData(city: String): Response<WeatherData> {
        return weatherDataRepository.getWeatherData(city)
    }

}