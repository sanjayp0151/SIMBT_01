package com.example.weatherapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.model.Forecast
import com.example.weatherapp.model.ForecastDay
import com.example.weatherapp.model.WeatherResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

import retrofit2.converter.gson.GsonConverterFactory

class SevenDayWeather : AppCompatActivity() {
    var data1 = ArrayList<LeftData>()
    var data = ArrayList<DownData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seven_day_weather)

        var location: String = intent.getStringExtra("location").toString()

        val backBtn : LinearLayout = findViewById(R.id.lL_back)


            val gson: Gson = GsonBuilder()
                .setLenient()
                .create()
            val retrofit = Retrofit.Builder()
                .baseUrl("http://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()

        val webInterface = retrofit.create(WebInterface::class.java)
        val call: Call<WeatherResponse> = webInterface.getWeatherForecast(
            "be66cffbd0de49258f2170318231307", // Replace with your API key
            "London",
            7,
            "yes",
            "no"
        )

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse: WeatherResponse? = response.body()
                    Toast.makeText(applicationContext,"success",Toast.LENGTH_LONG).show()
                    if (weatherResponse != null) {

                        val forecastResponse = response.body()
                        val forecastDayList = forecastResponse?.forecast?.forecastday ?: emptyList()

                        data = forecastDayList.map { convertToDownData(it) } as ArrayList<DownData>

                        Log.e("DataInList", data[0].date+data[0].temperature)
                        Log.e("DataInList", data[1].date+data[1].temperature)
                        if (forecastResponse != null) {
                            Log.e("DataInList", forecastResponse.forecast?.forecastDay?.get(0)?.day?.avgTemp_c.toString())
                        }

                    }
                } else {
                    // Handle error
                    Toast.makeText(applicationContext, "success$response",Toast.LENGTH_LONG).show()
                    response.errorBody()?.string()?.let { Log.d("Response", it) }
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                // Handle failure
                Toast.makeText(applicationContext, "Failed to load $t",Toast.LENGTH_LONG).show()
            }
        })








        backBtn.setOnClickListener{
            val intent = Intent(this@SevenDayWeather,MainActivity::class.java)
            startActivity(intent)

        }



        val recyclerview1 = findViewById<RecyclerView>(R.id.recycler_view_1)
        recyclerview1.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
//        recyclerview1.layoutManager = LinearLayoutManager(this)

        val adapter1 = LeftAdapter(data1)
        recyclerview1.adapter = adapter1






        val recyclerview2 = findViewById<RecyclerView>(R.id.recycler_view_2)
        recyclerview2.layoutManager = LinearLayoutManager(this)

        val adapter = DownAdapter(data)
        recyclerview2.adapter = adapter

    }
    private fun convertToDownData(forecastDay: ForecastDay): DownData {
        val cloudImage = R.drawable.cloud_img
        val temperature = forecastDay.day?.avgTemp_c
        val date = "Today , ${forecastDay.date}"

        return DownData(cloudImage, temperature.toString(), date)
    }
}