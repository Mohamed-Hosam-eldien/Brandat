package com.example.brandat.ui.fragments.myOrder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.orderModel.Orders
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MyOrderViewModel @Inject constructor(
    private var iProductsRepository: IProductsRepository
): ViewModel() {

    private var _getOrder : MutableLiveData<Response<Orders>> = MutableLiveData()
    var getOrder: LiveData<Response<Orders>> = _getOrder

    fun getOrdersFromApi(email:String?) =
        viewModelScope.launch {
            val result= iProductsRepository.getAllOrders(email)
            _getOrder.postValue(result)
        }


}