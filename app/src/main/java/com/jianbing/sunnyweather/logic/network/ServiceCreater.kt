package com.jianbing.sunnyweather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreater {
    private const val BASE_URL="https://api.caiyunapp.com"
    private val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    //这样创建出来的PlaceService实例是一个动态代理对象，可以直接使用
    fun <T> creat(serviceClass: Class<T>):T= retrofit.create(serviceClass)
    inline fun<reified T> create(): T= creat(T::class.java)
}