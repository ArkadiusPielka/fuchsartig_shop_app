package com.example.fuchsartig.remote

import com.example.fuchsartig.data.model.Product
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET

const val BASE_URL = "https://retoolapi.dev/uQes19/fuchsartig/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ProductApiService {

    @GET("uQes19/fuchsartig")
    suspend fun getProduct(): List<Product>

}

object ProductApi {
val retrofitService: ProductApiService by lazy { retrofit.create(ProductApiService::class.java) }
}