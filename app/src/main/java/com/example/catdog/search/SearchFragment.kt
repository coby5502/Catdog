package com.example.catdog.search

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catdog.R
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.*

class SearchFragment : Fragment() {
    lateinit var places_rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?)
    : View? {
        val rootview = inflater.inflate(R.layout.fragment_search, container, false)
        places_rv = rootview.findViewById(R.id.recyclerview_places) as RecyclerView
        return rootview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFinal()
    }

    private fun setFinal() {
        searchFinal.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val api = KakaoAPI.create()
                api.getSearchLocation(query, SearchViewModel.nowLng!!.toDouble(), SearchViewModel.nowLat!!.toDouble()).enqueue(object : Callback<Place> {
                    override fun onResponse(call: Call<Place>, response: Response<Place>) {
                        val result = response.body()?.documents

                        if(response.body()?.meta?.total_count == 0) {
                            Toast.makeText(context, "검색결과가 없습니다.", Toast.LENGTH_SHORT).show()
                        }

                        Log.d("Places", result.toString())

                        places_rv.adapter = PlaceListAdapter(result as MutableList<PlaceDocument>) { PlaceDocument->
                            SearchViewModel.resultPlace = PlaceDocument
                            SearchViewModel.resultLat = PlaceDocument.y
                            SearchViewModel.resultLng = PlaceDocument.x
                            Log.d("Place", SearchViewModel.resultPlace.toString())
                            findNavController().navigate(R.id.action_searchFragment_to_mainMap)
                        }

                        places_rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    }

                    override fun onFailure(call: Call<Place>, t: Throwable) {
                        Log.e(TAG,"FAIL")
                    }
                })

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })
    }
}