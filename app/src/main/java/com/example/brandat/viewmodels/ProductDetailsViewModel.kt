package com.example.brandat.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.Product
import com.example.brandat.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private var iProductsRepository: IProductsRepository
):ViewModel() {

    private var _getProduct : MutableLiveData<Product> = MutableLiveData()
    private var _setError = MutableLiveData<String>()

    var getProduct: LiveData<Product> = _getProduct
    var getError: LiveData<String> =_setError

    fun getProductDetailsFromDatabase(productId:Long){
        viewModelScope.launch {
            when(val result= iProductsRepository.getProductDetails(productId)){
                is NetworkResult.Success->  _getProduct.postValue(result.data!!)
                is NetworkResult.Error-> _setError.postValue(result.exception)
                else -> {

                }
            }

        }
    }
}