package com.example.brandat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.ProductDetails
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    var repoInterface: IProductsRepository
) : ViewModel() {

    var categoryResponse = MutableLiveData<List<ProductDetails>>()
    var categoryLive: LiveData<List<ProductDetails>> = categoryResponse
    private var _setError = MutableLiveData<String>()

    private val _isOrderd: MutableLiveData<Cart> = MutableLiveData<Cart>()
    var isOrderd: LiveData<Cart> = _isOrderd
    var getError: LiveData<String> = _setError

    var allProductResponse: MutableLiveData<List<ProductDetails>> = MutableLiveData()
    var productsLive: LiveData<List<ProductDetails>> = allProductResponse

    fun getCategory(productId: Long) = viewModelScope.launch {
        when (val result = repoInterface.getCategories(productId)) {
            is NetworkResult.Success -> categoryResponse.postValue(result.data?.products)
            is NetworkResult.Error -> _setError.postValue(result.exception)
            else -> {

            }
        }

    }

    fun getAllProductsByName() = viewModelScope.launch {
        when (val result = repoInterface.getAllProduct()) {
            is NetworkResult.Success -> allProductResponse.postValue(result.data?.products)
            is NetworkResult.Error -> _setError.postValue(result.exception)
            else -> {

            }
        }

    }

    fun getAllProductsByType(type: String) = viewModelScope.launch {
        val result = if(type == "ALL")
            repoInterface.getAllProduct()
        else
            repoInterface.getAllProductByType(type)

        when (result) {
            is NetworkResult.Success -> allProductResponse.postValue(result.data?.products)
            is NetworkResult.Error -> _setError.postValue(result.exception)
            else -> {

            }
        }

    }


}