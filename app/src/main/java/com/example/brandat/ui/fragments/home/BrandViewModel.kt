package com.example.brandat.ui.fragments.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.Brand
import com.example.brandat.models.orderModel.discount.PriceRules
import com.example.brandat.utils.NetworkResult
import com.example.brandat.utils.ResponseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrandViewModel @Inject constructor(
    private var brandRepository: IProductsRepository,application: Application
) : AndroidViewModel(application) {

    private var _brandResponse = MutableLiveData<List<Brand>>()
    private var _setError = MutableLiveData<String>()
    private var _setLoad = MutableLiveData<Int>()

    private var _discountCodes = MutableLiveData<ResponseResult<PriceRules>>()
    var discountCodes : LiveData<ResponseResult<PriceRules>> = _discountCodes


    var brandResponse:LiveData<List<Brand>> = _brandResponse
    var getError: LiveData<String> =_setError
    var getLoad: LiveData<Int> =_setLoad

    fun getBrands() = viewModelScope.launch {
        when (val result = brandRepository.getbrand()) {
            is NetworkResult.Success -> {
                _brandResponse.postValue(result.data?.brands)
            }
            is NetworkResult.Error -> {
                _setError.postValue(result.exception)
            }
            else -> {
            }
        }
    }

    fun getDiscountCode() {
        viewModelScope.launch(Dispatchers.IO) {
         val res = brandRepository.getDiscountCodes()
            _discountCodes.postValue(res)
        }
    }


}
