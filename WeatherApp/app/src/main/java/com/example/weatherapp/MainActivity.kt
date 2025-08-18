package com.example.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.ui.view.MainScreen
import com.example.weatherapp.ui.viewmodel.WeatherViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class MainActivity : ComponentActivity() {

    private val apiKey = "50443f4f78c4c4606348e5646dbcf92b"

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            val viewModel = remember { WeatherViewModel(WeatherRepository()) }

            val requestPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                    if (isGranted) {
                        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                            .addOnSuccessListener { location ->
                                location?.let {
                                    viewModel.fetchWeather(it.latitude, it.longitude, apiKey)
                                }
                            }
                    }
                }
            )

            MainScreen(
                viewModel = viewModel,
                onRequestLocation = {
                    when {
                        ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED -> {
                            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                                .addOnSuccessListener { location ->
                                    location?.let {
                                        viewModel.fetchWeather(it.latitude, it.longitude, apiKey)
                                    }
                                }
                        }
                        else -> {
                            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    }
                }
            )
        }
    }
}
