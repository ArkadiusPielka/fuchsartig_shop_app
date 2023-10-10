package com.example.fuchsartig.data.model

import com.squareup.moshi.Json

data class Product(
    @Json(name = "id") val apiId: Int = 0,
    val img1: String = "",
    val img2: String? = "",
    val img3: String? = "",
    val price: String = "",
    val title: String = "",
    var number: String = "",
    val category: String = "",
    val descript: String = "",
    val is_rated: String? = "",
    val user_rating: String? = "",
    val Testemonials: String? = "",
) {
    var liked: Boolean = false
    var selectedNumber: Int = 0
}
