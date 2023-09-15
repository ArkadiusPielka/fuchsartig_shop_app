package com.example.fuchsartig.data.model

data class Product(
    val id: Int,
    val img1: String,
    val img2: String?,
    val img3: String?,
    val price: String,
    val title: String,
    val number: String,
    val category: String,
    val descript: String,
    val is_rated: String?,
    val user_rating: String?,
    val Testemonials: String?
) {
    var is_liked: Boolean = false
}
