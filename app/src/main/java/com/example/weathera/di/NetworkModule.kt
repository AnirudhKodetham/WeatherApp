//package com.example.weathera.di
//
//
//
//import com.example.weathera.data.api.WeatherApiService
//import com.example.weathera.data.model.WeatherResponse
//import javax.inject.Inject
//import javax.inject.Singleton
//
//@Singleton
//class WeatherRepository @Inject constructor(
//    private val apiService: WeatherApiService
//) {
//    // TODO: Replace with your actual OpenWeatherMap API Key
//    private val apiKey = "YOUR_API_KEY_HERE"
//
//    /**
//     * Fetch weather using a city name string (e.g., "London" or "New York,NY,US")
//     */
//    suspend fun fetchWeatherByCity(city: String): Result<WeatherResponse> {
//        return try {
//            val response = apiService.getWeatherByCity(city, apiKey)
//            Result.success(response)
//        } catch (e: Exception) {
//            // Given more time, I would map these to specific domain errors
//            // (e.g., NoNetworkException vs CityNotFoundException)
//            Result.failure(e)
//        }
//    }
//
//    /**
//     * Fetch weather using GPS coordinates
//     */
//    suspend fun fetchWeatherByLocation(lat: Double, lon: Double): Result<WeatherResponse> {
//        return try {
//            val response = apiService.getWeatherByCoordinates(lat, lon, apiKey)
//            Result.success(response)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//}

package com.example.weathera.di

import com.example.weathera.data.api.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // This tells Hilt where this module lives
object NetworkModule {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }
}