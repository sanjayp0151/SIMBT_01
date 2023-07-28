package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class ForecastDay(
    val image : Day?,
    val date: String?,
    val day:Day?,
)