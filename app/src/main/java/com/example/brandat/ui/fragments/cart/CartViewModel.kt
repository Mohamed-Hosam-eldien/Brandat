package com.example.brandat.ui.fragments.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.products.IProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private var repo: IProductsRepository) : ViewModel() {
    private val _cartProduct: MutableLiveData<List<Cart>> = MutableLiveData<List<Cart>>()
    var cartProduct: LiveData<List<Cart>> = _cartProduct

    fun getAllCartproduct() = viewModelScope.launch {
        var result = repo.getAllCartProducts()
        _cartProduct.postValue(result)
        Log.e("Cart", "viewModel$result: ", )
    }

    fun removeProductFromCart(product: Cart) = viewModelScope.launch {
        repo.removeProductFromCart(product)
    }
    fun removeSelectedProductsFromCart(product: ArrayList<Cart>) = viewModelScope.launch {
        repo.removeSelectedProductsFromCart(product)
    }
}