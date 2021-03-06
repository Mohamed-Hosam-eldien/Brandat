package com.example.brandat.ui.fragments.orderStatus.checkOutOrder


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.products.IProductsRepository
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


@HiltViewModel
class CheckOutOrderViewModel @Inject constructor(private val repository: IProductsRepository) :
    ViewModel() {
    var orderProduct: List<Cart> = emptyList()
    val loading = MutableLiveData<Boolean>()
    private val _createOrderResponse = MutableLiveData<ResponseResult<OrderResponse>>()
    val createOrderResponse: LiveData<ResponseResult<OrderResponse>?> = _createOrderResponse
    var selectedPaymentMethods: String = "cash"
    var selectedAddress: CustomerAddress? = null
    var discount = 0.0
    var totalPrice = 0.0
    lateinit var currencyCode: String
    //fun getTotalPrice() = (discount ?: 0.0) + orderProduct.getPrice() + deliveryCoast

    fun createOrder() {

        val order = CustomerOrder(
           billing_address = convertToBillingAddress(selectedAddress!!),
            email = Constants.user.email,
            line_items = orderProduct.toLineItem(),
            gateway =selectedPaymentMethods,
            total_price = totalPrice.toString(),
            totalDiscount = discount.toString(),
            customer = Constants.user,
            sourceName = selectedAddress!!.printAddress(),
            currency = currencyCode
        )

        viewModelScope.launch(Dispatchers.IO) {
            val res = repository.createOrder(OrderModel(order = order))
            _createOrderResponse.postValue(res)
        }

    }


    init {
        viewModelScope.launch {
            val result = repository.getAllCartProducts()
            orderProduct = result
        }
    }


}