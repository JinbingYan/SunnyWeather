package com.jianbing.sunnyweather.ui.weather


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jianbing.sunnyweather.logic.Repository
import com.jianbing.sunnyweather.logic.model.Location

class WeatherVierModel: ViewModel() {
    private val locationLiveData=MutableLiveData<Location>()
    var locationLng=""     //可变，用var来声明
    var locationLat=""
    var placeName=""

    val weatherLiveData=Transformations.switchMap(locationLiveData){location->
        Repository.refreshWeather(location.lng,location.lat)
    }

    //提供给外界的接口，想要更新天气，调用refreshWeather函数即可
    fun  refreshWeather(lng:String,lat:String){
        locationLiveData.value=Location(lng,lat)
    }

}