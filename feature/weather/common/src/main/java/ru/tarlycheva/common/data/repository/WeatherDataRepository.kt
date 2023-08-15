package ru.tarlycheva.common.data.repository

import retrofit2.Response
import ru.tarlycheva.weather.data.api.WeatherService
import ru.tarlycheva.common.domain.model.WeatherData
import ru.tarlycheva.common.domain.repository.IWeatherDataRepository
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