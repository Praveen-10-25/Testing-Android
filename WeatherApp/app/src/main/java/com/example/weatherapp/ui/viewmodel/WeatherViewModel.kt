package com.example.weatherapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _weatherState = MutableStateFlow<Resource<Pair<Double, String>>>(Resource.Idle)
    val weatherState: StateFlow<Resource<Pair<Double, String>>> = _weatherState

    fun fetchWeather(lat: Double, lon: Double, apiKey: String) {
        viewModelScope.launch {
            _weatherState.value = Resource.Loading
            try {
                val response = repository.getWeather(lat, lon, apiKey)
                _weatherState.value = Resource.Success(response.main.temp to response.name)
            } catch (e: Exception) {
                _weatherState.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }
}
