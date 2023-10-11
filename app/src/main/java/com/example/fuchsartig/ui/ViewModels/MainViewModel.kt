package com.example.fuchsartig.ui.ViewModels

import android.app.Application
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fuchsartig.data.Repository
import com.example.fuchsartig.data.model.Product
import com.example.fuchsartig.data.model.ProductNumberUpdate
import com.example.fuchsartig.remote.ProductApi
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

enum class ApiLayoutStatus { LINEAR, GRID }

class MainViewModel(application: Application) : AndroidViewModel(application) {

//    private val dataBase = getInstance(application)


    private val repository = Repository(ProductApi)

    var products = repository.product

    private val _layout = MutableLiveData<ApiLayoutStatus>(ApiLayoutStatus.LINEAR)
    val layout: LiveData<ApiLayoutStatus>
        get() = _layout

    private val _currentProduct = MutableLiveData<Product>()
    val currentProduct: LiveData<Product>
        get() = _currentProduct

    val emptyProduct = mutableListOf<Product>()


    private val _currentImages = MutableLiveData<MutableList<String>>(mutableListOf())
    val currentImages: LiveData<MutableList<String>>
        get() = _currentImages


    init {
        loadProduct()
    }

    fun loadProduct() {
        viewModelScope.launch {
            try {
                repository.getProducts()
            } catch (e: Exception) {

            }
        }
    }

    fun updateProductNumber(updateProducts: List<Product>) {
        viewModelScope.launch {
            try {
                for (product in updateProducts) {

                    val apiId = product.apiId
                    val currentNumber = product.number.toInt()
                    val selectedNumber = product.selectedNumber
                    val newNumber = (currentNumber - selectedNumber).toString()

                    val productUpdate = ProductNumberUpdate(newNumber)

                    repository.updateProductNumber(apiId, productUpdate)
                }
            } catch (e: Exception) {

            }
        }
    }

    fun getEmptyProducts(products: List<Product>): List<Product> {
        val emptyProducts = mutableListOf<Product>()

        for (product in products) {
            if (product.number == "0" || product.number == "1") {
                emptyProducts.add(product)
            }
        }

        return emptyProducts
    }

    fun setApiLayoutStatus(layoutStatus: ApiLayoutStatus) {
        _layout.value = layoutStatus
    }

    fun updateLayout() {
        _layout.value = _layout.value
    }

    fun currentProduct(input: Product) {
        _currentProduct.postValue(input)
    }

    fun currentImages(input: Product) {

        _currentImages.value?.clear()

        val imageUrls = listOf(input.img1, input.img2, input.img3)

        for (imageUrl in imageUrls) {
            if (!imageUrl.isNullOrEmpty()) {
                _currentImages.value?.add(imageUrl)
            }
        }
        _currentImages.value = _currentImages.value
    }

    fun getUserRole(userRole: Boolean): Boolean {
        return userRole
    }

}