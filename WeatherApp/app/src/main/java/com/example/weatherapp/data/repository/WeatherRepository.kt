package com.example.weatherapp.data.repository

import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.network.RetrofitClient

class WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double, apiKey: String): WeatherResponse {
        return RetrofitClient.api.getWeather(lat, lon, apiKey)
    }
}