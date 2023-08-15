package ru.tarlycheva.data.repository

import retrofit2.Response
import ru.tarlycheva.data.api.WeatherService
import ru.tarlycheva.domain.model.WeatherData
import ru.tarlycheva.domain.repository.IWeatherDataRepository
import javax.inject.Inject

class WeatherDataRepository @Inject constructor(
    private val api: WeatherService
) : IWeatherDataRepository {

    override suspend fun getWeatherData(city: String): Response<WeatherData> {
        return api.getCurrentWeather(
                key = WEATHER_API_KEY,
                city = city,
                aqi = "no"
            )

    }

    companion object {
        private const val WEATHER_API_KEY = "93f35c51aad44beba6a191310232807"
    }


}