package ru.tarlycheva.userweather.data.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.tarlycheva.core.userLocation.domain.repository.LocationRepository
import ru.tarlycheva.core.userLocation.domain.service.LocationService
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UserWeatherModule {


}