package com.example.weatherapp.model

data class Forecast(
    val forecastDay: List<ForecastDay>?
) {
    val forecastday: List<ForecastDay>? =emptyList()
}