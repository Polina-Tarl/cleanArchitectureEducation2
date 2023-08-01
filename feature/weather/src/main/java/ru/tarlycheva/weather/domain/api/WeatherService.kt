package ru.tarlycheva.weather.domain.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.tarlycheva.weather.domain.model.WeatherData

interface WeatherService {

    @GET("/v1/current.json")
    suspend fun getCurrentWeather(
        @Query("key") key: String,
        @Query("q") city: String,
        @Query("aqi") aqi: String = "no"
    ) : Response<WeatherData>
}