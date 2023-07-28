package com.example.weatherapp.model

data class Current(
    val cloud: Int,
    val condition: Condition,
    val humidity: Int,
    val temp_c: Double,
    val wind_kph: Double,
)