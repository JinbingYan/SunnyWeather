package com.jianbing.sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jianbing.sunnyweather.logic.Repository
import com.jianbing.sunnyweather.logic.model.Place

class PlaceViewModel:ViewModel() {
    //不向外展示的，保存的是搜索的关键词
    private val searchLiveData= MutableLiveData<String>()

    //保存返回结果
    val placeList=ArrayList<Place>()
    //提供给个外界监视的，没当seaerchLiveDAta发生变化时触发
    val placeLiveData=Transformations.switchMap(searchLiveData){query->
        Repository.searchPlace(query)
    }
    //搜索框内容变化时触发这个searchPlace方法，然后更改value的值，然后被检测到，调用
//    Repository的searchPlace方法，通过网络请求获取数据，并把数据放到placeList里面
    fun searchPlaces(query:String){
        searchLiveData.value=query
    }
}