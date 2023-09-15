package com.example.fuchsartig.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fuchsartig.data.model.Product
import com.example.fuchsartig.remote.ProductApi

class Repository(private val api: ProductApi) {

    private val _product = MutableLiveData<List<Product>>()
    val product: LiveData<List<Product>>
        get() = _product

    suspend fun getProducts() {
        try {
            _product.postValue(api.retrofitService.getProduct())
        } catch (e: Exception){
        Log.e("data", "noload $e")
        }
    }
}
