package com.example.catdog.food

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catdog.R
import com.example.catdog.food.FoodViewModel.Companion.food
import com.example.catdog.food.FoodViewModel.Companion.type
import kotlinx.android.synthetic.main.fragment_food_search.*

class FoodSearchFragment : Fragment() {
    lateinit var foods_rv: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?)
            : View? {
        val rootview = inflater.inflate(R.layout.fragment_food_search, container, false)
        foods_rv = rootview.findViewById(R.id.recyclerview_foods) as RecyclerView
        init()
        return rootview
    }

    private fun init() {
        foods_rv.adapter = FoodListAdapter(food[type!!][0] as MutableList<String>) { Food ->
            findNavController().navigate(R.id.action_foodSearchFragment_to_foodDetailFragment)
        }
        foods_rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }
}