package com.example.brandat.ui.fragments.orderStatus.checkOutOrder

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.example.brandat.R
import com.example.brandat.databinding.FragmentFinshOrderStateBinding
import com.example.brandat.models.CustomerAddress
import com.example.brandat.ui.fragments.cart.CartViewModel
import com.example.brandat.utils.Constants
import com.example.brandat.utils.ResponseResult
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.cancel.OnCancel
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
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

import java.math.BigDecimal

@AndroidEntryPoint
class CheckOutOrderFragment : Fragment() {

    private val checkOutOrderViewModel: CheckOutOrderViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
     lateinit var sharedPreferences :SharedPreferences
    lateinit var onOkClickListener: OnOkClickListener
    lateinit var binding: FragmentFinshOrderStateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_finsh_order_state, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            checkOutOrderViewModel.selectedAddress = arguments?.getParcelable<CustomerAddress>("address")!!
            checkOutOrderViewModel.selectedPaymentMethods = arguments?.getString("paymentMethod")!!
            checkOutOrderViewModel.discount = arguments?.getDouble("discount")!!
            checkOutOrderViewModel.totalPrice = arguments?.getDouble("price")!!

        }
        sharedPreferences =requireActivity().getSharedPreferences(Constants.SHARD_NAME, Context.MODE_PRIVATE)
        checkOutOrderViewModel.currencyCode = sharedPreferences.getString(Constants.CURRENCY_TYPE, "EGP")!!

        initUi()
//        setUpPayPal()
        setPaypal()
        checkOutOrderViewModel.createOrderResponse.observe(viewLifecycleOwner) {

            when (it) {
                is ResponseResult.Success -> {
                    hideLoading()
                    cartViewModel.removeSelectedProductsFromCart(checkOutOrderViewModel.orderProduct)
                    deleteFromDraft()
                    successDialog()
                    removeDataFromPrefrence()
                }

                is ResponseResult.Error -> {
                    hideLoading()
                    failureDialog()
                    Log.e(TAG, "onViewCreated: ${it.message}", )
                }
            }
        }

        //button confirm
        binding.confirmOrder.setOnClickListener {
            showLoading()
            when (checkOutOrderViewModel.selectedPaymentMethods) {
                 "cash"-> checkOutOrderViewModel.createOrder()
                 "paypal"-> paypal()
                     //payPalPaymentMethod()

            }
        }
    }

       private  fun paypal(){
                   var price:Double = when(checkOutOrderViewModel.currencyCode){
            "USD"->checkOutOrderViewModel.totalPrice
            "EGP"-> checkOutOrderViewModel.totalPrice.div(18.0)
            else-> checkOutOrderViewModel.totalPrice
                   }
           PayPalCheckout.startCheckout(
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
                                           Amount(currencyCode = CurrencyCode.USD, value = "10.0")
                                       )
                                   )
                               )
                           createOrderActions.create(order)
                       },

                   )

       }
    private fun setPaypal(){

            PayPalCheckout.registerCallbacks(
                onApprove = OnApprove { approval ->
                    approval.orderActions.capture { i ->
                        checkOutOrderViewModel.createOrder()
                        Log.e(TAG, "setPaypal: ${approval.data}", )
                        Log.e(TAG, "setPaypbbbbal: ${i}", )

                    }
                },

                onCancel = OnCancel {
                        showCancelSnakebar()
                },

                onError = OnError { errorInfo ->
                    hideLoading()
                    Log.e(TAG, "setPaypal: ${errorInfo}", )
                     showErrorSnakebar()
                }
            )
        }


    private fun removeDataFromPrefrence() {
        Paper.book().delete("count")
    }

    private fun deleteFromDraft() {
        FirebaseDatabase.getInstance()
            .getReference(Constants.user.id.toString())
            .child("cart")
            .removeValue()
    }



    private fun showLoading() {
        binding.loading.visibility = View.VISIBLE
        binding.confirmOrder.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.loading.visibility = View.GONE
        binding.confirmOrder.visibility = View.VISIBLE

    }


    private fun initUi() {
        hideLoading()
        showPaymentMethod()
        showSelectedAddress()
        binding.totalPrice.text = checkOutOrderViewModel.totalPrice.toString().plus("  ").plus(checkOutOrderViewModel.currencyCode)
        binding.deliveryCoast.text = "100".plus("  ").plus(checkOutOrderViewModel.currencyCode)
        binding.orderPrice.text = (checkOutOrderViewModel.totalPrice!! + 100).toString().plus("  ").plus(checkOutOrderViewModel.currencyCode)

    }

    private fun showSelectedAddress() {
        binding.run {
            checkOutOrderViewModel.run {
                val customerAddress = checkOutOrderViewModel.selectedAddress
                if (customerAddress != null) {
                    address.text =
                        customerAddress.address1.plus(" , ").plus(customerAddress.city).plus(" , ")
                            .plus(customerAddress.country)
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
                    "cash" -> {
                        cardCash.visibility = View.VISIBLE
                    }
                    "paypal" -> {
                        cardPaypal.visibility = View.VISIBLE
                    }
                }
            }
        }
    }


//
//    private fun payPalPaymentMethod() {
//        var price = when(checkOutOrderViewModel.currencyCode){
//            "USD"-> Constants.totalPrice
//            "EGP"-> Constants.totalPrice?.div(18.0)
//            else-> Constants.totalPrice
//        }
//        var payment =PayPalPayment(
//                BigDecimal(price?:0.0),
//                "USD",
//                "Brandat",
//                PayPalPayment.PAYMENT_INTENT_SALE
//            )
//        val intent = Intent(activity, PaymentActivity::class.java)
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration)
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)
//        requestPaymentMethod.launch(intent)
//    }
//
//    private val payPalConfiguration =
//        PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
//            .clientId(Constants.PAYPAL_CLIENT_ID)
//
//
//    fun setUpPayPal() {
//        var intent = Intent(context, PayPalService::class.java)
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,payPalConfiguration)
//        this.requireActivity().startService(intent)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        requireActivity().stopService(Intent(requireActivity(), PayPalService::class.java))
//
//    }

//
//    private val requestPaymentMethod =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { data ->
//            val resultCode = data.resultCode
//
//            if (resultCode == Activity.RESULT_OK) {
//                val auth = data?.data?.getParcelableExtra<PayPalAuthorization>(PayPalProfileSharingActivity.EXTRA_RESULT_AUTHORIZATION)
//                val confirm = data?.data?.getParcelableExtra<PaymentConfirmation>(PaymentActivity.EXTRA_RESULT_CONFIRMATION)
//                if(confirm!=null){
//                checkOutOrderViewModel.createOrder()
//
//                }
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                        hideLoading()
//                        showCancelSnakebar()
//
//
//            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
//                          hideLoading()
//                          showErrorSnakebar()
//                Log.e(TAG, ":${resultCode} ", )
//            }
//
//        }

    private fun successDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val layoutView = layoutInflater.inflate(R.layout.successful_dialog, null)
        val dialogButton = layoutView.findViewById(R.id.btnDialog) as Button
        dialogBuilder.setView(layoutView)
        dialogBuilder.setCancelable(false)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
        dialogButton.setOnClickListener {
            alertDialog.dismiss()
//            Navigation.findNavController(requireActivity(), R.id.navHostFragment)
//                .navigate(
//                    R.id.action_checkOutOrderFragment_to_homeFragment2
//                )

            requireActivity().finish()

        }
    }

    private fun failureDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val layoutView = layoutInflater.inflate(R.layout.dialog_failure, null)
        val dialogButton = layoutView.findViewById(R.id.btnDialog) as Button
        dialogBuilder.setView(layoutView)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
        dialogButton.setOnClickListener {
            alertDialog.dismiss()
        }
    }
    private fun showCancelSnakebar() {
        Snackbar.make(requireView(), "Cancel", Snackbar.LENGTH_INDEFINITE)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).setBackgroundTint(
                resources.getColor(
                    R.color.black2
                )
            )
            .setActionTextColor(resources.getColor(R.color.white)).setAction("Close") {
            }.show()

    }
    private fun showErrorSnakebar() {
        Snackbar.make(requireView(), "something wrong happen ", Snackbar.LENGTH_INDEFINITE)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).setBackgroundTint(
                resources.getColor(
                    R.color.red
                )
            )
            .setActionTextColor(resources.getColor(R.color.white)).setAction("Close") {
            }.show()

    }



}
