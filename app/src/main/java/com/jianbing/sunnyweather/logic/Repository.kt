package com.jianbing.sunnyweather.logic

import androidx.lifecycle.liveData
import com.jianbing.sunnyweather.logic.model.Place
import com.jianbing.sunnyweather.logic.model.Weather
import com.jianbing.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import okhttp3.Dispatcher
import java.lang.Exception
import kotlin.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {
    fun searchPlace(query:String)= fire(Dispatchers.IO){
            val placeResponse=SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status=="ok"){
                val places=placeResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is${placeResponse.status}"))
            }
    }

    fun refreshWeather(lng:String,lat:String)=fire(Dispatchers.IO){
            coroutineScope {//创造一个挂起作用域，以便使用sync函数
                val deferredRealtime = async {
                    SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
                }
                val deferredDaily = async {
                    SunnyWeatherNetwork.getDailyWeather(lng, lat)
                }
                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()
                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                    val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                    Result.success(weather)
                } else {
                    Result.failure(
                        RuntimeException(
                            "realtime response status is ${realtimeResponse.status}" +
                                    "daily response status is ${dailyResponse.status}"
                        )
                    )
                }
            }
    }

    //定义一个自己的高几函数，简化try...catch的使用
    private fun<T>fire(context:CoroutineContext,block:suspend()->Result<T>)=
        liveData<Result<T>>(context){
            val result=try {
                block()
            }catch (e:Exception){
                Result.failure<T>(e)
            }
            emit(result)
        }
}