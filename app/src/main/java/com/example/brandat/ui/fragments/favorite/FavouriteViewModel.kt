package com.example.brandat.ui.fragments.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.Favourite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private var favouriteRepository: IProductsRepository):ViewModel() {

    private  var _getFavouriteProduct = MutableLiveData<Favourite>()
    var getFavouriteProduct :LiveData<Favourite> = _getFavouriteProduct

    private  var _getFavouriteProducts = MutableLiveData<List<Favourite>>()
    var getFavouriteProducts :LiveData<List<Favourite>> = _getFavouriteProducts

    fun getFavouriteProducts(){
        viewModelScope.launch {
         var result = favouriteRepository.getFavouriteProducts()
            _getFavouriteProducts.postValue(result)
        }
    }
    fun removeFavouriteProduct(productName:String){
        viewModelScope.launch {
            favouriteRepository.removeFavouriteProduct(productName)
        }
    }

    fun insertFavouriteProduct(favProduct: Favourite) {
        viewModelScope.launch {
            favouriteRepository.insertFavouriteProduct(favProduct)
        }
    }
        fun searchForFavouriteInDatabase(productName:String) {

            viewModelScope.launch {
               var result  = favouriteRepository.searchInFavouriteProducts(productName)
                  _getFavouriteProduct.postValue(result)

            }

        }


}