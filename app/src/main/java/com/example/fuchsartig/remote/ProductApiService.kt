package com.example.fuchsartig.remote

import com.example.fuchsartig.data.model.Product
import com.example.fuchsartig.data.model.ProductNumberUpdate
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Path

const val BASE_URL = "https://retoolapi.dev/"
const val token = "LvSX4X"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ProductApiService {

    @GET("/$token/fuchsartig")
    suspend fun getProduct(): List<Product>

    @PATCH("/$token/fuchsartig/{apiId}")
    suspend fun updateProductNumber(
        @Path("apiID") apiId: Int,
        @Body productNumberUpdate: ProductNumberUpdate
    ): Product

}

object ProductApi {
    val retrofitService: ProductApiService by lazy { retrofit.create(ProductApiService::class.java) }
}