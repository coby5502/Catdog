package com.example.catdog.food

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.catdog.R
import com.example.catdog.food.FoodViewModel.Companion.food
import com.example.catdog.food.FoodViewModel.Companion.type
import com.example.catdog.food.FoodViewModel.Companion.pos
import kotlinx.android.synthetic.main.fragment_food_detail.*

class FoodDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_food_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        food_name.text = food[type!!][0][pos!!].toString()
        food_img.setImageResource(food[type!!][1][pos!!] as Int)
        food_yn.text = "급여 여부 : " + food[type!!][2][pos!!].toString()
        food_discription.text = food[type!!][3][pos!!].toString()
    }
}