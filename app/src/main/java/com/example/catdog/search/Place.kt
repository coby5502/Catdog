package com.example.catdog.search

data class Place (
    val meta: PlaceMeta,
    val documents: List<PlaceDocument>
)

data class PlaceMeta(
    val total_count: Int
)

data class PlaceDocument (
    val address_name: String = "",
    val category_group_code: String = "",
    val category_group_name: String = "",
    val category_name: String = "",
    val distance: String = "",
    val id: Long? = null,
    val phone: String = "",
    val place_name: String = "",
    val place_url: String = "",
    val road_address_name: String = "",
    val x: Double? = null, // longitude
    val y: Double? = null //  latitude
)