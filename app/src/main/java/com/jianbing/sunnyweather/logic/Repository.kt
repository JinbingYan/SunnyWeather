package com.jianbing.sunnyweather.logic

import androidx.lifecycle.liveData
import com.jianbing.sunnyweather.logic.model.Place
import com.jianbing.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import java.lang.Exception
import java.lang.RuntimeException

object Repository {
    fun searchPlace(query:String)=liveData(Dispatchers.IO){
        val result=try {
            val placeResponse=SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status=="OK"){
                val places=placeResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is${placeResponse.status}"))
            }
        }catch (e:Exception){
            Result.failure<List<Place>>(e)//为什么这里的failure和前面的不一样
        }
        emit(result)
    }
}