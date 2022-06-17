package com.example.brandat.ui.fragments.orderStatus

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.BillingAddress
import com.example.brandat.models.OrderModel.*
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.ui.fragments.orderStatus.checkOutOrder.PaymentMethodsEnum
import com.example.brandat.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class OrderStateViewModel @Inject constructor(var repository: IProductsRepository) :ViewModel() {
    var discount :Double? = null
    var selectedAddress:BillingAddress? = null
    var deliveryCoast = 100
    val selectedPaymentMethods: PaymentMethodsEnum = PaymentMethodsEnum.Paypal








    }





