package com.example.fuchsartig.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fuchsartig.data.model.Product
import com.example.fuchsartig.data.model.ProductNumberUpdate
import com.example.fuchsartig.data.model.ProductPriceUpdate
import com.example.fuchsartig.remote.ProductApi

class Repository(private val api: ProductApi) {

    private val _product = MutableLiveData<List<Product>>()
    val product: LiveData<List<Product>>
        get() = _product

    suspend fun getProducts() {
        try {
            _product.postValue(api.retrofitService.getProduct())
        } catch (e: Exception) {
            Log.e("data", "noload $e")
        }
    }

    suspend fun updateProductNumber(apiId: Int, productUpdate: ProductNumberUpdate) {
        try {
            Log.d(TAG, "ProductNumber ${productUpdate.number}")
            val updateProduct = api.retrofitService.updateProductNumber(apiId, productUpdate)
            getProducts()
            Log.d(TAG, "updateProduct ${updateProduct.toString()}")
        } catch (e: Exception) {
            Log.e(TAG, "$e")
        }
    }

    suspend fun updateProductPrice(apiId: Int, productUpdate: ProductPriceUpdate) {
        try {
            Log.d(TAG, "ProductNumber ${productUpdate.price}")
            val updateProduct = api.retrofitService.updateProductPrice(apiId, productUpdate)
            getProducts()
            Log.d(TAG, "updateProduct ${updateProduct.toString()}")
        } catch (e: Exception) {
            Log.e(TAG, "$e")
        }
    }
}
