package com.jianbing.sunnyweather.ui.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jianbing.sunnyweather.R
import com.jianbing.sunnyweather.logic.model.Weather
import com.jianbing.sunnyweather.logic.model.getSky
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.forecast_item.*
import kotlinx.android.synthetic.main.life_index.*
import kotlinx.android.synthetic.main.now.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {
    val viewModel by lazy { ViewModelProvider.NewInstanceFactory().create(WeatherVierModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        if (viewModel.placeName.isEmpty()){
            viewModel.placeName=intent.getStringExtra("place_name")?:""
        }
        if (viewModel.locationLng.isEmpty()){
            viewModel.locationLng=intent.getStringExtra("location_lng")?:""
        }
        if (viewModel.locationLat.isEmpty()){
            viewModel.locationLat=intent.getStringExtra("location_lat")?:""
        }
        viewModel.weatherLiveData.observe(this, Observer {result->
            val weather=result.getOrNull();
            if (weather!=null){
                showWeatherInfo(weather)
            }else{
                Toast.makeText(this,"获取天气信息失败",Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
        viewModel.refreshWeather(viewModel.locationLng,viewModel.locationLat)
    }

    private fun showWeatherInfo(weather: Weather) {
        placeName.text=viewModel.placeName
        val realtime=weather.realtime
        val daily=weather.daily;

        currentTemp.text= "${realtime.temperature}℃"
        currentSky.text=realtime.skycon
        currentAQI.text="空气指数${realtime.airQuality.aqi.chn}"
        nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)

        forecastLayout.removeAllViews()
        val days=daily.temperature.size
        for (i in 0 until days){
            val skycon=daily.skycon[i]
            val temperature=daily.temperature[i]
            val view=LayoutInflater.from(this).inflate(
                R.layout.forecast_item,forecastLayout,false)
            val dateInfo=view.findViewById<TextView>(R.id.dateInfo)
            val skyIcon=view.findViewById<ImageView>(R.id.skyIcon)
            val skyInfo=view.findViewById<TextView>(R.id.skyInfo)
            val temperatureInfo=view.findViewById<TextView>(R.id.temperatureInfo)
            val simpleDateFormat=SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text=simpleDateFormat.format(skycon.date)
            val sky= getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text=sky.info
            val tempText="${temperature.min}~${temperature.max}℃"
            temperatureInfo.text=tempText
            forecastLayout.addView(view)
        }
        val lifeIndex=daily.lifeIndex
        coldRiskText.text=lifeIndex.coldRisk[0].desc
        dressingText.text=lifeIndex.dressing[0].desc
        ultravilotText.text=lifeIndex.ultraviolet[0].desc
        carWashingText.text=lifeIndex.carwashing[0].desc
        weatherLayout.visibility= View.VISIBLE
    }

}