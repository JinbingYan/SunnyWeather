package com.jianbing.sunnyweather.logic.dao

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.jianbing.sunnyweather.SunnyWeatherApplication
import com.jianbing.sunnyweather.logic.model.Place

object PlaceDao {
    fun savePlace(place:Place){
        sharedPreferences().edit(){
            putString("place", Gson().toJson(place))
        }
    }
    fun getPlace():Place{
        val place= sharedPreferences().getString("place","")
        return Gson().fromJson(place,Place::class.java)
    }
    fun isPlaceSaved()= sharedPreferences().contains("place")
    private fun sharedPreferences()=SunnyWeatherApplication.context
        .getSharedPreferences("sunny_weather",Context.MODE_PRIVATE)
}