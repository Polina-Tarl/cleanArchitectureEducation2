package ru.tarlycheva.common.domain.model

data class WeatherData(
    val current: CurrentWeather? = null
)

data class CurrentWeather(
    val temp_c: Double? = null,
    val condition: ConditionWeather? = null
)

data class ConditionWeather(
    val text: String? = null
)