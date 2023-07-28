package com.example.weatherapp


import com.example.weatherapp.model.WeatherResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebInterface {
    @GET("forecast.json")
    fun getWeatherForecast(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") numDays: Int,
        @Query("aqi") includeAqi: String,
        @Query("alerts") includeAlerts: String
    ): Call<WeatherResponse>
}