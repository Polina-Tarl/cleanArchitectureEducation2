package ru.tarlycheva.weather.di.modules

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.tarlycheva.weather.data.repository.WeatherDataRepository
import ru.tarlycheva.weather.data.useCase.WeatherDataUseCase
import ru.tarlycheva.weather.domain.api.WeatherService
import ru.tarlycheva.weather.domain.repository.IWeatherDataRepository
import ru.tarlycheva.weather.domain.useCase.IWeatherDataUseCase
import javax.inject.Named
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

    @Provides
    @Singleton
    fun provideWeatherUseCase(weatherDataRepository: WeatherDataRepository): IWeatherDataUseCase =
        WeatherDataUseCase(weatherDataRepository)

}