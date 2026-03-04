package com.example.weathera.data.model


import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("weather") val weather: List<WeatherInfo>?,
    @SerializedName("main") val main: MainData?,
    @SerializedName("name") val cityName: String?
)

data class MainData(
    @SerializedName("temp") val temp: Double,
    @SerializedName("humidity") val humidity: Int
)

data class WeatherInfo(
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
) {
    // Helper to get the full icon URL for Coil/Glide
    val iconUrl: String
        get() = "https://openweathermap.org/img/wn/$icon@2x.png"
}