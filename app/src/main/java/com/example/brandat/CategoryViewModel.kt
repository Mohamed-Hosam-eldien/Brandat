package com.example.brandat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.Products
import com.example.brandat.ui.fragments.cart.Cart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    var repoInterface: IProductsRepository
) : ViewModel() {

    var categoryResponse = MutableLiveData<Response<Products>>()
    var categoryLive : LiveData<Response<Products>> = categoryResponse

    private val _isOrderd: MutableLiveData<Cart> = MutableLiveData<Cart>()
    var isOrderd: LiveData<Cart> = _isOrderd

//    private val _isAdded: MutableLiveData<Cart> = MutableLiveData<Cart>()
//    var isAdded: LiveData<Cart> = _isAdded

    private var allProductResponse: MutableLiveData<Response<Products>> = MutableLiveData()
    var productsLive : LiveData<Response<Products>> = allProductResponse

    fun getCategory(productId: Long) = viewModelScope.launch {
        categoryResponse.postValue(repoInterface.getCategories(productId))
    }

    fun getAllProductsByName() = viewModelScope.launch {
        allProductResponse.postValue(repoInterface.getAllProduct())
    }

    fun getAllProductsByType(type:String) = viewModelScope.launch {
        allProductResponse.postValue(repoInterface.getAllProductByType(type))
    }


}