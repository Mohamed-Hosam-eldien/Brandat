package com.example.brandat.ui.fragments.serach

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.Product
import com.example.brandat.models.ProductDetails
import com.example.brandat.models.Products
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val iRepo: IProductsRepository) : ViewModel() {

    private val mutableSearch = MutableLiveData<List<ProductDetails>>()
    val liveSearch = mutableSearch

    fun getAllProduct() = viewModelScope.launch {
        //mutableSearch.postValue(iRepo.getAllProduct().body()?.products)
       // mutableSearch.postValue(iRepo.getAllProduct())
    }

}