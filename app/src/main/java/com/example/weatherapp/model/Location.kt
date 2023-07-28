package com.example.weatherapp.model

data class Location(
    val country: String,
    val lat: Double,
    val localtime: String,
    val lon: Double,
    val name: String,
    val region: String,
    val tz_id: String
)