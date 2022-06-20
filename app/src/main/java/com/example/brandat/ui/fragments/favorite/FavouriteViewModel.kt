package com.example.brandat.ui.fragments.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.Favourite
import com.example.brandat.models.draft.CustomerOrder
import com.example.brandat.models.draftOrder.CustomerDraftOrder
import com.example.brandat.models.draftOrder.DraftOrderResponse
import com.example.brandat.ordermodel.Order
import com.example.brandat.ordermodel.OrderModel
import com.example.brandat.test.OrderDraft
import com.example.brandat.test.OrderResponse
import com.example.brandat.ui.fragments.orderDetails.OrderItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private var favouriteRepository: IProductsRepository
) : ViewModel() {

    private var _getFavouriteProduct = MutableLiveData<Favourite>()
    var getFavouriteProduct: LiveData<Favourite> = _getFavouriteProduct

    private var _getFavouriteProducts = MutableLiveData<List<Favourite>>()
    var getFavouriteProducts: LiveData<List<Favourite>> = _getFavouriteProducts

    private var _getFavouriteDraft = MutableLiveData<Response<com.example.brandat.ordermodel.OrderResponse>>()
    var getFavouriteDraftModel: LiveData<Response<com.example.brandat.ordermodel.OrderResponse>> = _getFavouriteDraft



    fun getFavouriteProducts() {
        viewModelScope.launch {
            var result = favouriteRepository.getFavouriteProducts()
            _getFavouriteProducts.postValue(result)
        }
    }

    fun removeFavouriteProduct(productId: Long) {
        viewModelScope.launch {
            favouriteRepository.removeFavouriteProduct(productId)
        }
    }

    fun insertFavouriteProduct(favProduct: Favourite) {
        viewModelScope.launch {
            favouriteRepository.insertFavouriteProduct(favProduct)
        }
    }

    fun searchForFavouriteInDatabase(productName: String) {

        viewModelScope.launch {
            val result = favouriteRepository.searchInFavouriteProducts(productName)
            _getFavouriteProduct.postValue(result)
        }

    }

    fun postDatToApi(draftModel: com.example.brandat.models.draft.OrderModel) {
        viewModelScope.launch {
            _getFavouriteDraft.postValue(favouriteRepository.postFavDraft(draftModel))
        }
    }

}