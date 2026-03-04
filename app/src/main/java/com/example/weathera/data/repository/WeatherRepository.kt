package com.example.weathera.data.repository



import com.example.weathera.data.api.WeatherApiService
import com.example.weathera.data.model.WeatherResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val apiService: WeatherApiService
) {
    // TODO: Replace with your actual OpenWeatherMap API Key
    private val apiKey = "bfb49961fa4821e89e3abcbf5f69789c"

    /**
     * Fetch weather using a city name string (e.g., "London" or "New York,NY,US")
     */
    suspend fun fetchWeatherByCity(city: String): Result<WeatherResponse> {
        return try {
            val response = apiService.getWeatherByCity(city, apiKey)
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace() // This will show the real cause in Logcat (e.g., 401, 404, or UnknownHostException)
            Result.failure(e)
        }
    }

    /**
     * Fetch weather using GPS coordinates
     */
    suspend fun fetchWeatherByLocation(lat: Double, lon: Double): Result<WeatherResponse> {
        return try {
            val response = apiService.getWeatherByCoordinates(lat, lon, apiKey)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}