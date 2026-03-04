package com.example.weathera.viewmodel



import com.example.weathera.data.model.WeatherResponse

sealed interface WeatherUiState {
    object Idle : WeatherUiState
    object Loading : WeatherUiState
    data class Success(val data: WeatherResponse) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}