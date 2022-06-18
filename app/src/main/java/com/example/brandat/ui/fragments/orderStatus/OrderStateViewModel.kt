package com.example.brandat.ui.fragments.orderStatus

import androidx.lifecycle.*
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.BillingAddress
import com.example.brandat.ui.fragments.orderStatus.checkOutOrder.PaymentMethodsEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderStateViewModel @Inject constructor(var repository: IProductsRepository) :ViewModel() {
    var discount :Double? = null
    var selectedAddress:BillingAddress? = null
    var deliveryCoast = 100
    var selectedPaymentMethods: PaymentMethodsEnum = PaymentMethodsEnum.Paypal








    }





