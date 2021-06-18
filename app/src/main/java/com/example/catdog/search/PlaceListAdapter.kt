package com.example.catdog.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catdog.R
import kotlinx.android.synthetic.main.recycler_places.view.*
import kotlin.math.roundToInt

class PlaceListAdapter(places_data: MutableList<PlaceDocument>, val itemClick: (PlaceDocument) -> Unit)
    : RecyclerView.Adapter<PlaceListAdapter.PlaceViewHolder>() {

    var places: MutableList<PlaceDocument> = places_data

    inner class PlaceViewHolder(parent: ViewGroup, itemClick: (PlaceDocument) -> Unit) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_places, parent, false)) {
        val PlaceName = itemView.place_name
        val PlaceDistance = itemView.place_distance
        val PlaceAddress = itemView.place_address
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PlaceViewHolder(parent, itemClick)

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        places[position].let { place ->
            with(holder) {
                PlaceName.text = place.place_name
                PlaceAddress.text = place.address_name

                var dis1: Int = place.distance.toInt()
                var dis2: String
                if(dis1>=1000) {
                    dis2 = ((dis1 / 100).toDouble().roundToInt()/10f).toString() + "km"
                }
                else {
                    dis2 = dis1.toString() + "m"
                }
                PlaceDistance.text = dis2
                itemView.setOnClickListener{ itemClick(place) }
            }
        }
    }

    override fun getItemCount() = places.size
}