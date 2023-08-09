package ru.tarlycheva.weather.data.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.tarlycheva.weather.data.repository.WeatherDataRepository
import ru.tarlycheva.weather.data.api.WeatherService
import ru.tarlycheva.weather.domain.repository.IWeatherDataRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherService): IWeatherDataRepository =
        WeatherDataRepository(api)

}