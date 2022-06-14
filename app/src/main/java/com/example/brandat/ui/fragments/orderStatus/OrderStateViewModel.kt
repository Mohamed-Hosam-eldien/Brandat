package com.example.brandat.ui.fragments.orderStatus

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.BillingAddress
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
//    var orderProduct :List<MyClass> = emptyList()
    var discount :Double? = null
    var selectedAddress:BillingAddress? = null
    var deliveryCoast = 100
    val loading = MutableLiveData<Boolean>()
    var createOrderResult = MutableLiveData<ResponseResult<Unit,ResponseError>>()


    val selectedPaymentMethods: PaymentMethodsEnum = PaymentMethodsEnum.Paypal
//    fun startCheck() {
//        PayPalCheckout.startCheckout(
//            CreateOrder { createOrderActions ->
//                val order =
//                    com.paypal.checkout.order.Orders(
//                        intent = OrderIntent.CAPTURE,
//                        appContext = AppContext(userAction = UserAction.PAY_NOW),
//                        purchaseUnitList =
//                        listOf(
//                            PurchaseUnit(
//                                amount =
//                                Amount(currencyCode = CurrencyCode.USD, value = "10.00")
//                            )
//                        )
//                    )
//
//                createOrderActions.create(order)
//            }
        //)
//    }


//    fun startCheck() {
//        CreateOrder { createOrderActions ->
//                val order =
//                    Orders(
//                        intent = OrderIntent.CAPTURE,
//                        appContext = AppContext(userAction = UserAction.PAY_NOW),
//                        purchaseUnitList =
//                        listOf(
//                            PurchaseUnit(
//                                amount =
//                                Amount(currencyCode = CurrencyCode.USD, value = "10.00")
//                            )
//                        )
//                    )
//                createOrderActions.create(order)
//            }
//
//
//
//    }


//    fun getTotalPrice() = (discount ?: 0.0) + orderProduct.getPrice() + deliveryCoast



    fun confirmOrder() {
        viewModelScope.launch(Dispatchers.IO) {
            when (selectedPaymentMethods) {
                PaymentMethodsEnum.Cash -> startConfirm()
                PaymentMethodsEnum.Paypal -> {
                    startConfirm()
//                    startCheck()
                }
            }
        }

    }

    private suspend fun startConfirm(){
//        val result = createOrder()
//            if (result.isSuccessful){
//                 //createOrderResult.postValue( ResponseResult.Success(Unit))
//                Log.d(TAG, "startConfirm: d3d3 ---> done")
//           }
//            else{
//                Log.e(TAG, "startConfirm: $result")
//                Log.e(TAG, "startConfirm: "+result.code())
//
//                createOrderResult.postValue(ResponseResult.Error(ResponseError.ServerError,result.errorBody()?.string()))
//          }
    }


//    private  suspend fun createOrder(): Response<OrderResponse> {
//       val order = CustomerOrder(

//           transactions = listOf(
//               Transactions(
//                   kind = "sale",
//                   status = "success",
//                   amount = 100.0
//               )
//           ),
//          billing_address = add(),
//           shipping_address=addd(),
//           email = "doaaessam2021@gmail.com" ,
//           line_items= ss()
//
//
//       )

//        Log.d(TAG, "d3d3: ${order.orders[0].line_items}")
//
//        val currentOrder = Orders()
//        currentOrder.orders = listOf(order)
        
//        return when (selectedPaymentMethods) {
//           PaymentMethodsEnum.Cash -> {
//               repository.createOrder(OrderModel(order =order ))
//           }
//           PaymentMethodsEnum.Paypal -> {
//               repository.createOrder(OrderModel(order))
//           }
//       }
//   }


    init {
        viewModelScope.launch {
           var result =  repository.getAllCartProducts()
//            orderProduct = result

        }



    }
//     private fun fake():List<Cart>{
//         var list = ArrayList<Cart>()
//         list.add(
//             Cart("CONVERSE | CHUCK TAYLOR ALL STAR II HI - 4 / black",10,"",1,42845086056706,7706249232642
//             ,))
//         return  list
//     }
//      private fun  add ():com.example.brandat.models.myModel.BillingAddress{
//          return com.example.brandat.models.myModel.BillingAddress("Ap #417-5876 Mus. St.","","Neuruppin","" ,
//              "Pakistan","PK","Steven","Ewing"
//              ,0.0,0.0,
//              "Steven Ewing","+92515761234","Brandenburg", "","82623"
//          )
//
//      }
//    private fun  addd ():ShippingAddress{
//        return ShippingAddress("Ap #417-5876 Mus. St.","","Neuruppin","" ,
//            "Pakistan","PK","Steven","Ewing"
//            ,0.0,0.0,
//            "Steven Ewing","+92515761234","Brandenburg", "","82623"
//        )
//
//    }
//    private  fun ss(): ArrayList<LineItem> {
//        var list = ArrayList<LineItem>()
//        list.add(LineItem(42845086056706,1))
////        val order = Order()
////        order.line_items = list
////        order.email = "doaaessam2021@gmail.com"
////        order.fulfillment_status = "fulfilled"
//
//
////        var orderList = listOf<Order>(
////            order
////        )
//        return list
//    }

}


