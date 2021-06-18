package com.example.catdog.food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.catdog.R
import com.example.catdog.food.FoodViewModel.Companion.type
import kotlinx.android.synthetic.main.fragment_food.*

class FoodFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootview = inflater.inflate(R.layout.fragment_food, container, false)

        return rootview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_fruit.setOnClickListener {
            findNavController().navigate(R.id.action_foodFragment_to_foodSearchFragment)
            type = 0
        }

        btn_veg.setOnClickListener {
            findNavController().navigate(R.id.action_foodFragment_to_foodSearchFragment)
            type = 1
        }

        btn_meat.setOnClickListener {
            findNavController().navigate(R.id.action_foodFragment_to_foodSearchFragment)
            type = 2
        }

        btn_fish.setOnClickListener {
            findNavController().navigate(R.id.action_foodFragment_to_foodSearchFragment)
            type = 3
        }

        btn_nuts.setOnClickListener {
            findNavController().navigate(R.id.action_foodFragment_to_foodSearchFragment)
            type = 4
        }
    }
}