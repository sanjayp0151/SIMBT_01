package com.example.weatherapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.example.weatherapp.model.WeatherResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var sevenDayBtn : Button
    lateinit var location_icon : ImageView
    lateinit var location_name : TextView

    lateinit var currentDate : TextView
    lateinit var currentDegree : TextView
    lateinit var currentCondition : TextView
    lateinit var currentWindSpeed : TextView
    lateinit var currentHum : TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sevenDayBtn = findViewById(R.id.detail_btn)
        location_icon = findViewById(R.id.location_icon)
        location_name= findViewById(R.id.location_name)

        currentDate = findViewById(R.id.tv_date_main)
        currentCondition = findViewById(R.id.tv_weatherCondition_main)
        currentDegree = findViewById(R.id.tv_degree_main)
        currentWindSpeed = findViewById(R.id.tv_wind_value)
        currentHum = findViewById(R.id.tv_hum_value)

        sevenDayBtn.setOnClickListener {

            val intent = Intent(this@MainActivity,SevenDayWeather::class.java)
            intent.putExtra("location","London")
            startActivity(intent)

        }



        getWeatherInfo()



    }

    private fun getWeatherInfo() {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
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

                        location_name.text = "${weatherResponse.location.name} ${weatherResponse.location.region}"
                        var date = weatherResponse.location.localtime
                        currentDate.text = "Today , ${date}"

                        val degree :Int = weatherResponse.current.temp_c.toInt()
                        currentDegree.text = degree.toString()

                        currentWindSpeed.text = weatherResponse.current.wind_kph.toString()
                        currentHum.text = weatherResponse.current.humidity.toString()

                        currentCondition.text = weatherResponse.current.condition.text

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








    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val menuItem : MenuItem? = menu?.findItem(R.id.action_search)
        val searchView : SearchView= menuItem?.actionView as SearchView

        searchView.queryHint = "Type here to search.."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("Not yet implemented")
            }

        })


        return super.onCreateOptionsMenu(menu)
    }
}


