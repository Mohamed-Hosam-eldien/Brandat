package com.example.brandat.ui.fragments.serach

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.ProductDetails
import com.example.brandat.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val iRepo: IProductsRepository) : ViewModel() {

    private val mutableSearch = MutableLiveData<List<ProductDetails>>()
    private var _setError = MutableLiveData<String>()
    val liveSearch = mutableSearch

    fun getAllProduct() = viewModelScope.launch {
        //mutableSearch.postValue(iRepo.getAllProduct().body()?.products)
        val result = iRepo.getAllProduct()
        when (result) {
            is NetworkResult.Success -> mutableSearch.postValue(result.data?.products)
            is NetworkResult.Error -> _setError.postValue(result.exception.message)
            else -> {

            }
        }

    }

}