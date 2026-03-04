package com.example.weathera.UserInterface



import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.weathera.viewmodel.WeatherUiState
import com.example.weathera.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var cityName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Search Bar
        TextField(
            value = cityName,
            onValueChange = { cityName = it },
            label = { Text("Enter US City (e.g. Dallas,TX)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.getWeatherByCity(cityName) },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Search Weather")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // UI State Handling
        when (val state = uiState) {
            is WeatherUiState.Loading -> CircularProgressIndicator()
            is WeatherUiState.Success -> WeatherDetails(state.data)
            is WeatherUiState.Error -> Text(state.message, color = MaterialTheme.colorScheme.error)
            is WeatherUiState.Idle -> Text("Search for a city or allow location access")
        }
    }
}

@Composable
fun WeatherDetails(data: com.example.weathera.data.model.WeatherResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Use elvis operator (?:) to provide fallbacks for null values
            Text(
                text = data.cityName ?: "Unknown City",
                style = MaterialTheme.typography.headlineLarge
            )

            // Safely access the first weather icon
            val weatherInfo = data.weather?.firstOrNull()

            AsyncImage(
                model = weatherInfo?.iconUrl,
                contentDescription = "Weather Icon",
                modifier = Modifier.size(100.dp)
            )

            // Safely access temperature - default to 0 if null
            val temperature = data.main?.temp?.toInt() ?: 0
            Text(
                text = "$temperature°F",
                style = MaterialTheme.typography.displayMedium
            )

            Text(
                text = weatherInfo?.description?.uppercase() ?: "NO DESCRIPTION",
                style = MaterialTheme.typography.bodyLarge
            )

            // Safely access humidity
            val humidity = data.main?.humidity ?: 0
            Text(text = "Humidity: $humidity%")
        }
    }
}