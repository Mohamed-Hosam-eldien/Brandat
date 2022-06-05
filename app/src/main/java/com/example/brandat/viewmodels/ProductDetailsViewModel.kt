package com.example.brandat.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.Product
import com.example.brandat.models.Products
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@ActivityRetainedScoped
class ProductDetailsViewModel @Inject constructor(
    private var iProductsRepository: IProductsRepository
):ViewModel() {

    private var _getProduct : MutableLiveData<Response<Product>> = MutableLiveData()
    var getProduct: LiveData<Response<Product>> = _getProduct

    fun getProductDetailsFromDatabase(productId:Long){
        Log.d("TAG", "getProductDetailsFromDatabase: ")
        viewModelScope.launch {
            var result= iProductsRepository.getProductDetails(productId)
            _getProduct.postValue(result)
        }
    }
}