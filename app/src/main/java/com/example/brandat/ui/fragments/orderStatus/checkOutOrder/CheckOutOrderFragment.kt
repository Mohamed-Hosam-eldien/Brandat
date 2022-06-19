package com.example.brandat.ui.fragments.orderStatus.checkOutOrder

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.brandat.R
import com.example.brandat.databinding.FragmentFinshOrderStateBinding
import com.example.brandat.models.CustomerAddress
import com.example.brandat.models.orderModel.ShippingAddress
import com.example.brandat.ui.fragments.orderStatus.OrderStateViewModel
import com.example.brandat.utils.ResponseResult
import com.google.android.material.snackbar.Snackbar
import com.paypal.android.sdk.payments.*
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.cancel.OnCancel
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.error.OnError
import com.paypal.checkout.order.Amount
import com.paypal.checkout.order.AppContext
import com.paypal.checkout.order.Order
import com.paypal.checkout.order.PurchaseUnit
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.json.JSONException
import java.math.BigDecimal


@AndroidEntryPoint
class CheckOutOrderFragment : Fragment() {

    private val checkOutOrderViewModel : CheckOutOrderViewModel by viewModels()
    private val mainStateOrderViewModel : OrderStateViewModel by viewModels()

      lateinit var binding:FragmentFinshOrderStateBinding
      override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding=DataBindingUtil.inflate(inflater,R.layout.fragment_finsh_order_state, container, false)
     return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
           checkOutOrderViewModel.selectedAddress = arguments?.getParcelable<CustomerAddress>("address")!!
           checkOutOrderViewModel.selectedPaymentMethods = arguments?.getString("paymentMethod")!!
            Log.e(TAG, "onViewCreatedthree:${arguments?.getString("paymentMethod")!!} " )
            Log.e(TAG, "onViewCreatedthree:${arguments?.getParcelable<CustomerAddress>("address")!!.address1} " )

        }

        initUi()
               setUpPayPal()
        checkOutOrderViewModel.createOrderResponse.observe(viewLifecycleOwner){

                  when(it){
                     is ResponseResult.Success ->{
                         Log.e(TAG, ": d3d3d3${it.data}")

                         showSnakebar(R.string.operation_succeeded)
                         runBlocking {
                             delay(300)
                             requireActivity().finish()

                         }
                     }

                     is  ResponseResult.Error -> {
                         Log.e(TAG, "onViewCreated: d3d3d3${it.message.toString()}")
                         showSnakebar(R.string.operation_faild)

                     }
                  }
            }

        //button confirm
        binding.confirmOrder.setOnClickListener {
            showLoading()
            when (mainStateOrderViewModel.selectedPaymentMethods) {
                PaymentMethodsEnum.Cash -> checkOutOrderViewModel.createOrder()
                PaymentMethodsEnum.Paypal -> {
                    payPalPaymentMethod()
                }
            }


        }

        }


    private fun showSnakebar(message:Int) {
        val snackBar = Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        )
        snackBar.view.setBackgroundColor(Color.GREEN)
        snackBar.show()
        hideLoading()

    }

    private fun showLoading(){
        binding.loading.visibility=View.VISIBLE
        binding.confirmOrder.visibility=View.GONE
    }

    private fun hideLoading(){
        binding.loading.visibility=View.GONE
        binding.confirmOrder.visibility=View.VISIBLE
    }


    private fun initUi() {
         showPaymentMethod()
         showSelectedAddress()
//        binding.tvTotalProductsPrice.text ="99"
//        binding.tvDelivary.text = "100"
//       binding.tvDiscount.text = "20"
//        binding.tvTotal.text = "320"
    }

    private fun showSelectedAddress() {
        binding.run {
            mainStateOrderViewModel.run {
               address.text=  checkOutOrderViewModel.selectedAddress.toString()
            }
        }

    }

    private fun showPaymentMethod() {
        binding.run {
            mainStateOrderViewModel.run {
                cardCash.visibility = View.GONE
                cardPaypal.visibility = View.GONE

                when (selectedPaymentMethods) {
                    PaymentMethodsEnum.Cash -> {
                        cardCash.visibility = View.VISIBLE
                    }
                    PaymentMethodsEnum.Paypal -> {
                        cardPaypal.visibility = View.VISIBLE
                    }
                }
            }
        }

        }



    fun paypal() {
        PayPalCheckout.registerCallbacks(
            onApprove = OnApprove { approval ->
                approval.orderActions.capture { _ ->
                    checkOutOrderViewModel.createOrder()
                }
            },

            onCancel = OnCancel {

                Toast.makeText(
                    context,
                    "canceled",
                    Toast.LENGTH_SHORT
                ).show()
            },

            onError = OnError { errorInfo ->

                Toast.makeText(
                    context,
                    "error",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("paypal_onError", "paypal: " + errorInfo)
            }
        )
    }

    private fun payPalPaymentMethod() {
        var payment =
            PayPalPayment(BigDecimal(1), "USD", "Shopify", PayPalPayment.PAYMENT_INTENT_SALE)
        val intent = Intent(activity, PaymentActivity::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration)
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)
        requestPaymentMethod.launch(intent)
    }
    private val payPalConfiguration =
        PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)


    fun setUpPayPal() {
        var intent = Intent(context, PayPalService::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration)
        this.requireActivity().startService(intent)
    }


    fun startCheck() {
        PayPalCheckout.startCheckout(
            CreateOrder { createOrderActions ->
                val order =
                    Order(
                        intent = OrderIntent.CAPTURE,
                        appContext = AppContext(userAction = UserAction.PAY_NOW),
                        purchaseUnitList =
                        listOf(
                            PurchaseUnit(
                                amount =
                                Amount(currencyCode = CurrencyCode.USD, value = "10.00")
                            )
                        )
                    )

                createOrderActions.create(order)
            }
        )
    }

    private val requestPaymentMethod =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { data ->
            val resultCode = data.resultCode

            if (resultCode == Activity.RESULT_OK) {
                  checkOutOrderViewModel.createOrder()
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i(ContentValues.TAG, "The user canceled.")
                Toast.makeText(context, "Payment Canceled!", Toast.LENGTH_SHORT).show()

            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                    ContentValues.TAG,
                    "An invalid Payment or PayPalConfiguration was submitted. Please see the docs."
                )
                Toast.makeText(context, "Invalid Payment Data!", Toast.LENGTH_SHORT).show()
            }

        }

//    private fun showDiscount(value: String? = null): Double? {
//        binding.run {
//            return if (value == null) {
//                rowDiscount.visibility = View.GONE
//                null
//            } else {
//                rowDiscount.visibility = View.VISIBLE
//                value.toDouble()
//            }
//        }
//    }







}



