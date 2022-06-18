package com.example.brandat.ui.fragments.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brandat.utils.NetworkResult
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.Brand
import com.example.brandat.models.Brands
import com.example.brandat.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrandViewModel @Inject constructor(
    private var brandRepository: IProductsRepository, application: Application
) : AndroidViewModel(application) {


    private var _brandResponse = MutableLiveData<List<Brand>>()
    private var _setError = MutableLiveData<String>()
    private var _setLoad = MutableLiveData<Int>()

    var brandResponse:LiveData<List<Brand>> = _brandResponse
    var getError: LiveData<String> =_setError
    var getLoad: LiveData<Int> =_setLoad

    fun getBrands() = viewModelScope.launch {
        val result = brandRepository.getbrand()
        Log.e("TAG", "====result====$result ", )
        when (result) {
            is NetworkResult.Success -> {
                _brandResponse.postValue(result.data?.brands)
            }
            is NetworkResult.Error -> {
                _setError.postValue(result.exception.message)
            }

            else -> {
                Log.e("TAG", "wheen========:", )
            }
        }
    }

    private fun offlineCacheBrands(brand: Brands) {
        // val brandEntity = BrandsEntity(brand)
        //insertBrand(brandEntity)
    }

}
