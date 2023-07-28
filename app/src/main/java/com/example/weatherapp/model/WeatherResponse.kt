package com.example.weatherapp.model

data class WeatherResponse(
    val current: Current,
    val forecast: Forecast?,
    val location: Location
)