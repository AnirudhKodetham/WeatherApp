package com.example.weathera.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathera.data.repository.WeatherRepository
import com.example.weathera.util.SharedPrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val prefs: SharedPrefsManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val uiState: StateFlow<WeatherUiState> = _uiState

    init {
        // Auto-load last city on launch
        val lastCity = prefs.getLastCity()
        if (lastCity != null) {
            getWeatherByCity(lastCity)
        }
    }

//    fun getWeatherByCity(city: String) {
//        viewModelScope.launch {
//            _uiState.value = WeatherUiState.Loading
//            repository.fetchWeatherByCity(city)
//                .onSuccess {
//                    _uiState.value = WeatherUiState.Success(it)
//                    prefs.saveLastCity(city)
//                }
//                .onFailure { _uiState.value = WeatherUiState.Error("City not found or network error") }
//        }
//    }

    // In WeatherViewModel.kt
    fun getWeatherByCity(city: String) {
        if (city.isBlank()) {
            _uiState.value = WeatherUiState.Error("Please enter a city name")
            return
        }

        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            repository.fetchWeatherByCity(city)
                .onSuccess { response ->
                    _uiState.value = WeatherUiState.Success(response)
                    prefs.saveLastCity(city)
                }
                .onFailure { error ->

                    _uiState.value = WeatherUiState.Error(error.localizedMessage ?: "Check connection")
                }
        }
    }

    fun getWeatherByLocation(lat: Double, lon: Double) {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            repository.fetchWeatherByLocation(lat, lon)
                .onSuccess { _uiState.value = WeatherUiState.Success(it) }
                .onFailure { _uiState.value = WeatherUiState.Error("Could not fetch location weather") }
        }
    }
}