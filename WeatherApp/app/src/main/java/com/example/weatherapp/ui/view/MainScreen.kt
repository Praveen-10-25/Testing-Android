package com.example.weatherapp.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.ui.viewmodel.WeatherViewModel
import com.example.weatherapp.utils.Resource

@Composable
fun MainScreen(
    viewModel: WeatherViewModel,
    onRequestLocation: () -> Unit
) {
    val state by viewModel.weatherState.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (state) {
                is Resource.Idle -> {
                    Button(onClick = { onRequestLocation() }) {
                        Text("Get Weather")
                    }
                }
                is Resource.Loading -> {
                    CircularProgressIndicator()
                }
                is Resource.Success -> {
                    val (temp, city) = (state as Resource.Success<Pair<Double, String>>).data
                    Text("City: $city")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Temperature: $temp °C")
                }
                is Resource.Error -> {
                    Text("Error: ${(state as Resource.Error).message}", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
