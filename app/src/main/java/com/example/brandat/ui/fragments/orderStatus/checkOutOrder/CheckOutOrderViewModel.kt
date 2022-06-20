package com.example.brandat.ui.fragments.orderStatus.checkOutOrder

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.BillingAddress
import com.example.brandat.models.CustomerAddress
import com.example.brandat.models.orderModel.*
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.utils.Constants
import com.example.brandat.utils.ResponseResult
import com.example.brandat.utils.convertToBillingAddress
import com.example.brandat.utils.toLineItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class CheckOutOrderViewModel @Inject constructor(private val repository: IProductsRepository) :ViewModel() {
    var orderProduct :List<Cart> = emptyList()
    val loading = MutableLiveData<Boolean>()
    private val _createOrderResponse= MutableLiveData<ResponseResult<OrderResponse>>()
    val createOrderResponse: LiveData<ResponseResult<OrderResponse>?> = _createOrderResponse
    var selectedPaymentMethods:String = "Paypal"
    var selectedAddress: CustomerAddress? = null
    var discount = 0.0

    //fun getTotalPrice() = (discount ?: 0.0) + orderProduct.getPrice() + deliveryCoast

    fun createOrder() {
        val order = CustomerOrder(
              billing_address= convertToBillingAddress(selectedAddress!!),
              email = "d3d3@gmail.com" ,
              line_items=orderProduct.toLineItem(),
               gateway= selectedPaymentMethods ,
               total_price = Constants.totalPrice.toString(),
               totalDiscount = discount.toString(),
              customer = Constants.user

        )

        viewModelScope.launch (Dispatchers.IO){
            var res = repository.createOrder(OrderModel(order = order))
                    _createOrderResponse.postValue(res)
                }

    }


    init {
        viewModelScope.launch {
            var result =  repository.getAllCartProducts()
            orderProduct = result

        }



    }




}