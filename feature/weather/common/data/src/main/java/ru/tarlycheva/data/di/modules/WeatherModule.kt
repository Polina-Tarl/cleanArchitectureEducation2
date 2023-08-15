package ru.tarlycheva.data.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.tarlycheva.data.repository.WeatherDataRepository
import ru.tarlycheva.data.api.WeatherService
import ru.tarlycheva.data.useCase.WeatherDataUseCase
import ru.tarlycheva.domain.repository.IWeatherDataRepository
import ru.tarlycheva.domain.useCase.IWeatherDataUseCase
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
    fun provideWeatherUseCase(repository: WeatherDataRepository): IWeatherDataUseCase =
        WeatherDataUseCase(repository)

}