package com.jianbing.sunnyweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SunnyWeatherNetwork {//是个单例类
    private val placeService=ServiceCreater.creat(PlaceService::class.java)

    private val weatherService=ServiceCreater.creat(WeatherService::class.java)

    suspend fun searchPlaces(query:String)= placeService.searchPlace(query).await()

    suspend fun getRealtimeWeather(lng:String,lat:String)=
        weatherService.getRealtimeWeahter(lng,lat).await()

    suspend fun getDailyWeather(lng:String,lat:String)=
        weatherService.getDailyWeather(lng,lat).await()

    //定义一个任何Call实例均可调用的await函数，仅限于private
    private suspend fun <T>Call<T>.await():T{//拓展函数
        return suspendCoroutine {continuaton->
            enqueue(object : Callback<T> {//object有什么用，起什么作用   声明匿名内部类
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body=response.body()
                    if(body!=null)continuaton.resume(body)
                    else continuaton.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuaton.resumeWithException(t)
                }

            })
        }
    }
}