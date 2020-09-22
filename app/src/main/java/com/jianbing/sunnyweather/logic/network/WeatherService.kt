package com.jianbing.sunnyweather.logic.network

import com.jianbing.sunnyweather.SunnyWeatherApplication
import com.jianbing.sunnyweather.logic.model.DailyResponse
import com.jianbing.sunnyweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeahter(@Path("lng")lng:String,@Path("lat")lat:String)
            : Call<RealtimeResponse>

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng")lng:String,@Path("lat")lat:String)
            :Call<DailyResponse>
}