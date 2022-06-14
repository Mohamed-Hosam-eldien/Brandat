package com.example.brandat.ui.fragments.orderStatus.checkOutOrder

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.brandat.R
import com.example.brandat.databinding.FragmentFinshOrderStateBinding
import com.example.brandat.ui.fragments.orderStatus.OrderStateViewModel
import com.example.brandat.utils.Constants.Companion.PAYPAL_CLIENT_ID
import com.example.brandat.utils.ResponseResult
import com.paypal.android.sdk.payments.*
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.order.Amount
import com.paypal.checkout.order.AppContext
import com.paypal.checkout.order.Order
import com.paypal.checkout.order.PurchaseUnit
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigDecimal


@AndroidEntryPoint
class FinishOrderStateFragment : Fragment() {

    private val finishOrderViewModel by lazy {
       FinishOrderStateViewModel()
    }

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
          setUpPayPal()
              initUi()
        mainStateOrderViewModel.createOrderResult.observe(viewLifecycleOwner){

                  when(it){
                     is  ResponseResult.Error -> makeSnakebar("error")
                      is ResponseResult.Success -> messageDialog()
                      else -> makeSnakebar("error")
                  }
            }


        //paymentMethod
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

        //address
        binding.run {
            mainStateOrderViewModel.run {
//                cvCurrentLocation.run {
//                    address = selectedAddress?.address1
//                    address2 = selectedAddress?.address
//                    title = selectedAddress?.city
//                    setImageFromUrl(selectedAddress?.getLatLng())
//                    refresh()
                }
            }



        //button confirm
        binding.confirmOrder.setOnClickListener {
            finishOrderViewModel.loading.postValue(true)
//             mainStateOrderViewModel.confirmOrder()
            payPalPaymentMethod()
        }
    }

    private fun makeSnakebar(s: String) {
        Log.e(TAG, "makeSnakebar: errrrrrrr", )
    }



    private fun initUi() {
        binding.tvTotalProductsPrice.text = "200"
        binding.tvDelivary.text = "100"
       binding.tvDiscount.text = "20"
        binding.tvTotal.text = "320"
    }


    private fun paypal() {
      PayPalCheckout.start(
            createOrder =
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
            },
            onApprove =
            OnApprove { approval ->
                approval.orderActions.capture { captureOrderResult ->
                    Log.i("CaptureOrder", "CaptureOrderResult: $captureOrderResult")
                }
            }
        )
    }

    private fun payPalPaymentMethod() {
        var payment =
            PayPalPayment(BigDecimal(1), "USD", "Shopify", PayPalPayment.PAYMENT_INTENT_SALE)
        val intent = Intent(activity, PaymentActivity::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,payPalConfiguration)
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)
        requestPaymentMethod.launch(intent)
    }
    private val payPalConfiguration =
        PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId(PAYPAL_CLIENT_ID).sandboxUserPassword("87654321")
    private fun setUpPayPal() {
        var intent = Intent(activity, PaymentActivity::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration)
        this.requireActivity().startService(intent)
    }
    private val requestPaymentMethod =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { data ->
            val resultCode = data.resultCode

            if (resultCode == AppCompatActivity.RESULT_OK) {
                Toast.makeText(context, " D3D3", Toast.LENGTH_SHORT).show()
            }

        }

    private fun messageDialog() {
        context?.let {
            AlertDialog.Builder(it).apply {
                setNeutralButton(getString(R.string.message_order_don)) { d, i ->
                    activity?.finish()
                }
                setTitle(getString(R.string.message_order_don))
            }.create().show()
        }
    }
    private fun showDiscountRow(value: String? = null): Double? {
        binding.run {
            return if (value == null) {
                rowDiscount.visibility = View.GONE
                null
            } else {
                rowDiscount.visibility = View.VISIBLE
                value.toDouble()
            }
        }
    }

}



