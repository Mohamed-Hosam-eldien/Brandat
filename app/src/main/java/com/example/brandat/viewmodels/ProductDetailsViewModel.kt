package com.example.brandat.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.Product
import com.example.brandat.models.Products
import com.example.brandat.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

//@ActivityRetainedScoped
@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private var iProductsRepository: IProductsRepository
):ViewModel() {

    private var _getProduct : MutableLiveData<Product> = MutableLiveData()
    private var _setError = MutableLiveData<String>()

    var getProduct: LiveData<Product> = _getProduct
    var getError: LiveData<String> =_setError

    fun getProductDetailsFromDatabase(productId:Long){
        Log.d("TAG", "getProductDetailsFromDatabase: ")
        viewModelScope.launch {
            val result= iProductsRepository.getProductDetails(productId)
            when(result){
                is NetworkResult.Success->  _getProduct.postValue(result.data!!)
                is NetworkResult.Error-> _setError.postValue(result.exception.message)
                else -> {

                }
            }

        }
    }
}