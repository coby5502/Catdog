package com.example.catdog.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        var nowLat: Double? = 35.157662
        var nowLng: Double? = 129.059111

        var resultLat: Double? = null
        var resultLng: Double? = null
        var resultPlace: PlaceDocument? = null
    }
}