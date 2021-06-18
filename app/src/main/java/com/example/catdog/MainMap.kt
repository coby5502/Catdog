package com.example.catdog

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catdog.search.*
import com.example.catdog.search.SearchViewModel.Companion.nowLat
import com.example.catdog.search.SearchViewModel.Companion.nowLng
import com.example.catdog.search.SearchViewModel.Companion.resultLat
import com.example.catdog.search.SearchViewModel.Companion.resultLng
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainMap : Fragment(), OnMapReadyCallback {
    private var mLocationSource: FusedLocationSource? = null
    private var mNaverMap: NaverMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootview = inflater.inflate(R.layout.fragment_map, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment

        // getMapAsync를 호출하여 비동기로 onMapReady 콜백 메서드 호출
        // onMapReady에서 NaverMap 객체를 받음
        mapFragment.getMapAsync(this);
        mLocationSource = FusedLocationSource(this, PERMISSION_REQUEST_CODE)

        CoroutineScope(Dispatchers.Main).launch {
            if (resultLat != null) {
                ToPlace.text = "도착지 : " + SearchViewModel.resultPlace?.place_name.toString()
            }
        }

        find()

        return rootview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ToPlace.setOnClickListener {
            findNavController().navigate(R.id.action_mainMap_to_searchFragment)
        }

        cameraView.setOnClickListener {
            findNavController().navigate(R.id.action_mainMap_to_foodFragment)
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        // NaverMap 객체 받아서 NaverMap 객체에 위치 소스 지정
        mNaverMap = naverMap
        mNaverMap!!.locationSource = mLocationSource

        // 오버레이
        val locationOverlay = naverMap.locationOverlay
        locationOverlay.isVisible = true
        locationOverlay.icon = OverlayImage.fromResource(R.drawable.dog)
        locationOverlay.iconWidth = 100
        locationOverlay.iconHeight = 100

        naverMap.addOnLocationChangeListener { location ->
            nowLat = location.latitude
            nowLng = location.longitude
        }

        // 도착지 마커
        if(resultLat != null) {
            val marker = Marker()
            marker.position = LatLng(resultLat!!, resultLng!!)
            marker.map = mNaverMap
        }

        // UI 컨트롤 재배치
        val uiSettings = mNaverMap!!.uiSettings
        uiSettings.isCompassEnabled = true // 기본값 : true
        uiSettings.isScaleBarEnabled = true // 기본값 : true
        uiSettings.isZoomControlEnabled = true // 기본값 : true
        uiSettings.isLocationButtonEnabled = true // 기본값 : false

        // 권한확인. 결과는 onRequestPermissionsResult 콜백 매서드 호출
        requestPermissions(PERMISSIONS, PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // request code와 권한획득 여부 확인
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mNaverMap!!.locationTrackingMode = LocationTrackingMode.Follow
            }
        }
    }

    private fun find() {
        val api = KakaoAPI.create()
        api.getSearchLocation("펫", SearchViewModel.nowLng!!.toDouble(), SearchViewModel.nowLat!!.toDouble()).enqueue(object : Callback<Place> {
            override fun onResponse(call: Call<Place>, response: Response<Place>) {
                val result = response.body()?.documents

                if(response.body()?.meta?.total_count == 0) {
                    Toast.makeText(context, "검색결과가 없습니다.", Toast.LENGTH_SHORT).show()
                }

                Log.d("Places", result.toString())

                for(place in result!!) {
                    val marker = Marker()
                    val location = LatLng(place.y!!, place.x!!)
                    marker.position = location
                    marker.captionText = place.place_name
                    marker.captionColor = Color.rgb(2,186,133)
                    marker.map = mNaverMap
                }
            }

            override fun onFailure(call: Call<Place>, t: Throwable) {
                Log.e(ContentValues.TAG,"FAIL")
            }
        })
    }

    companion object {
        private const val TAG = "Map"
        private const val PERMISSION_REQUEST_CODE = 100
        private val PERMISSIONS = arrayOf<String>(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}