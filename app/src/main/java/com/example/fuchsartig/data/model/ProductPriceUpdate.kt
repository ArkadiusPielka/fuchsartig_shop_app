package com.example.fuchsartig.data.model

import com.squareup.moshi.Json

data class ProductPriceUpdate(@Json(name = "price") var price: String)
