package com.example.catdog.food

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catdog.R
import com.example.catdog.food.FoodViewModel.Companion.pos
import kotlinx.android.synthetic.main.recycler_foods.view.*
import java.util.ArrayList

class FoodListAdapter(foods_data: MutableList<String>, val itemClick: (String) -> Unit)
    : RecyclerView.Adapter<FoodListAdapter.FoodViewHolder>() {

    var foods: MutableList<String> = foods_data

    inner class FoodViewHolder(parent: ViewGroup, itemClick: (String) -> Unit) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.recycler_foods, parent, false)) {
        val FoodName = itemView.food_name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FoodViewHolder(parent, itemClick)

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        foods[position].let { food ->
            with(holder) {
                FoodName.text = food
                itemView.setOnClickListener{
                    pos = position
                    itemClick(food)
                }
            }
        }
    }

    override fun getItemCount() = foods.size
}