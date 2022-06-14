package com.example.brandat.ui.fragments.home

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brandat.NetworkResult
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.Brands
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BrandViewModel @Inject constructor(
    private var brandRepository: IProductsRepository,application: Application
) : AndroidViewModel(application) {



    private var _brandResponse = MutableLiveData<Response<Brands>>()
    var brandResponse = _brandResponse

    fun getBrands() = viewModelScope.launch {
        var result = brandRepository.getbrand()

       // brandResponse.postValue()//loading
        if (result is NetworkResult.Success<*>) {
            _brandResponse.postValue(result.data!! as Response<Brands>?)
        } else {
            _brandResponse.postValue(result)
        }
        brandResponse.postValue(result)

    }

    private fun offlineCacheBrands(brand: Brands) {
       // val brandEntity = BrandsEntity(brand)
        //insertBrand(brandEntity)
    }

    private fun handleBrandsResponse(response: Response<Brands>): NetworkResult<Brands> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.brands.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not found.")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


}
