package com.jianbing.sunnyweather.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.jianbing.sunnyweather.MainActivity
import com.jianbing.sunnyweather.R
import com.jianbing.sunnyweather.logic.model.Place
import com.jianbing.sunnyweather.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.place_item.view.*

class PlaceAdapter(private val fragment: PlaceFragment,private val placeList: List<Place>):
        RecyclerView.Adapter<PlaceAdapter.ViewHolder>(){
    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val placeName:TextView=view.findViewById(R.id.placeName)
        val palceAddress:TextView=view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val view=LayoutInflater.from(parent.context).inflate(R.layout.place_item,parent,false)
         val viewHolder=ViewHolder(view)

         viewHolder.itemView.setOnClickListener{
             val position=viewHolder.adapterPosition
             val place=placeList[position]
             val activity=fragment.activity
             if (activity is MainActivity){
                 Toast.makeText(fragment.context,"MainActivity",Toast.LENGTH_SHORT).show()
                 val intent=Intent(parent.context,WeatherActivity::class.java).apply {
                     putExtra("location_lng",place.location.lng)
                     putExtra("location_lat",place.location.lat)
                     putExtra("place_name",place.name)
                 }
                 //点击某个城市后，记录下来
                 fragment.startActivity(intent)
                 fragment.activity?.finish()
             }
             else if (activity is WeatherActivity){
                 Toast.makeText(fragment.context,"weatherActivity",Toast.LENGTH_SHORT).show()
                 activity.drawerLayout.closeDrawers()
                 activity.viewModel.locationLat=place.location.lat
                 activity.viewModel.locationLng=place.location.lng
                 activity.viewModel.placeName=place.name
                 activity.refreshWeather()
             }
             fragment.viewModel.savePlace(place)
         }
         return viewHolder
    }

    override fun getItemCount(): Int =placeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place=placeList[position]
        holder.placeName.text=place.name
        holder.palceAddress.text=place.address

    }
}