package com.example.weathera.UserInterface



import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.weathera.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.gms.location.LocationServices

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)) {
                fetchCurrentLocation()
            }
        }

        setContent {
            WeatherScreen(viewModel)
        }

        // Trigger permission dialog on launch
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))
    }

    private fun fetchCurrentLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    viewModel.getWeatherByLocation(it.latitude, it.longitude)
                }
            }
        } catch (e: SecurityException) {
            // Handle permission denial gracefully
        }
    }
}