package com.example.brandat.ui.fragments.orderStatus.checkOutOrder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.BillingAddress
import com.example.brandat.models.OrderModel.*
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.utils.Constants
import com.example.brandat.utils.ResponseResult
import com.example.brandat.utils.toLineItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CheckOutOrderViewModel @Inject constructor(private val repository: IProductsRepository) :ViewModel() {
    var orderProduct :List<Cart> = emptyList()
    val loading = MutableLiveData<Boolean>()
    private val _createOrderResponse: MutableLiveData<ResponseResult<OrderResponse>?> = MutableLiveData()
    val createOrderResponse: LiveData<ResponseResult<OrderResponse>?> = _createOrderResponse

    //fun getTotalPrice() = (discount ?: 0.0) + orderProduct.getPrice() + deliveryCoast

    fun createOrder(selectedPaymentMethods: PaymentMethodsEnum ) {
        val order = CustomerOrder(
            billingAddress= add(),
            shipping_address = addd(),
            line_items=orderProduct.toLineItem(),
            customer = Constants.user


        )
        viewModelScope.launch (Dispatchers.IO){
            when (selectedPaymentMethods) {

                PaymentMethodsEnum.Cash -> {
                    var res = repository.createOrder(OrderModel(order = order.copy(financialStatus = FinancialStatus.PayPal.toString())))
                    _createOrderResponse.postValue(res)
                }
                PaymentMethodsEnum.Paypal -> {
                    var res =
                        repository.createOrder(OrderModel(order.copy(financialStatus = FinancialStatus.Cash.toString())))
                    _createOrderResponse.postValue(res)

                }
            }
        }
    }


    init {
        viewModelScope.launch {
            var result =  repository.getAllCartProducts()
            orderProduct = result

        }



    }

    private fun  add ():com.example.brandat.models.OrderModel.BillingAddress{
        return com.example.brandat.models.OrderModel.BillingAddress("Ap #417-5876 Mus. St.","","Neuruppin","" ,
            "Pakistan","PK","Steven","Ewing"
            ,0.0,0.0,
            "Steven Ewing","+92515761234","Brandenburg", "","82623"
        )

    }
    private fun  addd (): ShippingAddress {
        return ShippingAddress("Ap #417-5876 Mus. St.","","Neuruppin","" ,
            "Pakistan","PK","Steven","Ewing"
            ,0.0,0.0,
            "Steven Ewing","+92515761234","Brandenburg", "","82623"
        )

    }

}