package com.example.brandat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.Products
import com.example.brandat.ui.fragments.cart.Cart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    var repoInterface: IProductsRepository
) : ViewModel() {

    var categoryResponse: MutableLiveData<Response<Products>> = MutableLiveData()

    fun getCategory(productId: Long) = viewModelScope.launch {
        categoryResponse.postValue(repoInterface.getCategories(productId))
    }

    fun addProductToCart(cart: Cart) = viewModelScope.launch {
        repoInterface.addProductToCart(cart)
    }

}