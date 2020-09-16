package com.jianbing.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication :Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")//suppresslink什么意思，有什么用
        //标注忽略括号内指定的警告，这里是StaticFieldleak
        //应该是静态变量要第一时间复制，但是我们这里在程序启动的时候一定会创建一个
        //sunnyweatherapplication对象，所以可以不用第一时间赋值，所以告诉系统你别管我
        lateinit var context: Context
        //此处填我们之后会在各个地方使用的彩云天气令牌号
        const val TOKEN=""
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }
}