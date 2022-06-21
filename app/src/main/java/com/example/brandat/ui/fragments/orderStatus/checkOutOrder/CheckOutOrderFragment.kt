package com.example.brandat.ui.fragments.orderStatus.checkOutOrder

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.brandat.R
import com.example.brandat.databinding.FragmentFinshOrderStateBinding
import com.example.brandat.models.CustomerAddress
import com.example.brandat.ui.MainActivity
import com.example.brandat.ui.fragments.cart.CartFragment
import com.example.brandat.ui.fragments.cart.CartViewModel
import com.example.brandat.ui.fragments.orderStatus.OrderStateViewModel
import com.example.brandat.utils.Constants
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
import io.paperdb.Paper
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.json.JSONException
import java.math.BigDecimal


@AndroidEntryPoint
class CheckOutOrderFragment : Fragment() {

    private val checkOutOrderViewModel : CheckOutOrderViewModel by viewModels()
    private val cartViewModel : CartViewModel by viewModels()
 var currency = "USD"
    lateinit var onOkClickListener: OnOkClickListener
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
           checkOutOrderViewModel.discount =  arguments?.getDouble("discount")!!
        }
        Paper.init(requireContext())
        // currency
        var  sharedPreferences =requireActivity().getSharedPreferences(Constants.SHARD_NAME, Context.MODE_PRIVATE)
        currency  = sharedPreferences.getString(Constants.CURRENCY_TYPE,"USD")!!
               initUi()
               setUpPayPal()
        checkOutOrderViewModel.createOrderResponse.observe(viewLifecycleOwner){

                  when(it){
                     is ResponseResult.Success ->{
                         hideLoading()
                         cartViewModel.removeSelectedProductsFromCart(checkOutOrderViewModel.orderProduct)
                          successDialog()

                     }

                     is  ResponseResult.Error -> {
                          hideLoading()
                           failureDialog()
                     }
                  }
        }

        //button confirm
        binding.confirmOrder.setOnClickListener {
            showLoading()
            when (checkOutOrderViewModel.selectedPaymentMethods) {
                "cash" -> checkOutOrderViewModel.createOrder()
                 "paypal" -> payPalPaymentMethod()

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
        binding.confirmOrder.isEnabled=false
    }

    private fun hideLoading(){
        binding.loading.visibility=View.GONE
        binding.confirmOrder.isEnabled=true
    }


    private fun initUi() {
         showPaymentMethod()
         showSelectedAddress()
       binding.totalPrice.text =Constants.totalPrice.toString().plus(" ").plus(currency)
       binding.deliveryCoast.text = "100" .plus(" ").plus(currency)
       binding.orderPrice.text= (Constants.totalPrice!!+ 100).toString().plus(" ").plus(currency)

    }

    private fun showSelectedAddress() {
        binding.run {
            checkOutOrderViewModel.run {
               var customerAddress =  checkOutOrderViewModel.selectedAddress
                if (customerAddress != null) {
                    address.text= customerAddress.address1.plus(" , ").plus(customerAddress.city).plus(" , ").plus(customerAddress.country)
                }

            }
        }

    }

    private fun showPaymentMethod() {
        binding.run {
            checkOutOrderViewModel.run {
                cardCash.visibility = View.GONE
                cardPaypal.visibility = View.GONE

                when (selectedPaymentMethods) {
                    selectedPaymentMethods -> {
                        cardCash.visibility = View.VISIBLE
                    }
                    selectedPaymentMethods -> {
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

    private val requestPaymentMethod =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { data ->
            val resultCode = data.resultCode

            if (resultCode == Activity.RESULT_OK) {
                val auth = data?.data?.getParcelableExtra<PayPalAuthorization>(PayPalProfileSharingActivity.EXTRA_RESULT_AUTHORIZATION)
                val confirm =

                    data?.data?.getParcelableExtra<PaymentConfirmation>(PaymentActivity.EXTRA_RESULT_CONFIRMATION)
                if (confirm != null) {
                    try {
                        checkOutOrderViewModel.createOrder()

                        Log.e(TAG, ": auth ${auth}" )
                        Log.i(TAG, confirm.toJSONObject().toString(4))
                        Log.i(TAG, confirm.payment.toJSONObject().toString(4))

                    } catch (e: JSONException) {
                        Log.e(TAG, "an extremely unlikely failure occurred: ", e)
                        Toast.makeText(context,
                            "Payment failed please try Again!",
                            Toast.LENGTH_SHORT).show()
                    }

                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                showSnakebar(R.string.cancel)


            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {

                showSnakebar(R.string.invalid_data)
            }

        }

   private  fun successDialog(){
       var  dialogBuilder = AlertDialog.Builder(requireContext())
       var layoutView = layoutInflater.inflate(R.layout.successful_dialog,null)
       var dialogButton = layoutView.findViewById(R.id.btnDialog) as Button
       dialogBuilder.setView(layoutView)
       var   alertDialog = dialogBuilder.create()
       alertDialog.show()
       dialogButton.setOnClickListener(View.OnClickListener {
           alertDialog.dismiss()
           Navigation.findNavController(requireActivity(),R.id.navHostFragment)
               .navigate(
                   R.id.action_checkOutOrderFragment_to_homeFragment2
               )
           requireActivity().finish()

       })
   }

    private  fun failureDialog(){
        var  dialogBuilder = AlertDialog.Builder(requireContext())
        var layoutView = layoutInflater.inflate(R.layout.dialog_failure, null)
        var dialogButton = layoutView.findViewById(R.id.btnDialog) as Button
        dialogBuilder.setView(layoutView)
        var   alertDialog = dialogBuilder.create()
        alertDialog.show()
        dialogButton.setOnClickListener(View.OnClickListener {
            alertDialog.dismiss() })
    }



}



