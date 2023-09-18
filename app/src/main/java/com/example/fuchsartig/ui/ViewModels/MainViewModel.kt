package com.example.fuchsartig.ui.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fuchsartig.data.Repository
import com.example.fuchsartig.data.model.Product
import com.example.fuchsartig.remote.ProductApi
import kotlinx.coroutines.launch

enum class ApiLayoutStatus { LINEAR, GRID }

class MainViewModel(application: Application) : AndroidViewModel(application) {

//    private val dataBase = getInstance(application)

    private val repository = Repository(ProductApi)

    val products = repository.product

    private val _layout = MutableLiveData<ApiLayoutStatus>(ApiLayoutStatus.LINEAR)
    val layout: LiveData<ApiLayoutStatus>
        get() = _layout

    private val _currentProduct = MutableLiveData<Product>()
    val currentProduct: LiveData<Product>
        get() = _currentProduct

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

    fun setApiLayoutStatus(layoutStatus: ApiLayoutStatus) {
        _layout.value = layoutStatus
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
}