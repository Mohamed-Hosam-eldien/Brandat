package com.example.brandat.ui.fragments.orderStatus.checkOutOrder

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.brandat.R
import com.example.brandat.databinding.FragmentFinshOrderStateBinding
import com.example.brandat.ui.fragments.orderStatus.OrderStateViewModel
import com.example.brandat.utils.ResponseResult
import com.google.android.material.snackbar.Snackbar
import com.paypal.android.sdk.payments.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
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
               initUi()
        checkOutOrderViewModel.createOrderResponse.observe(viewLifecycleOwner){

                  when(it){
                     is ResponseResult.Success ->{
                         showSnakebar(R.string.operation_succeeded)
                         runBlocking {
                             delay(300)
                             requireActivity().finish()
                         }
                     }

                     is  ResponseResult.Error -> {
                         showSnakebar(R.string.operation_faild)

                     }
                  }
            }

        //button confirm
        binding.confirmOrder.setOnClickListener {
            showLoading()
            checkOutOrderViewModel.createOrder(mainStateOrderViewModel.selectedPaymentMethods)


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
        binding.tvTotalProductsPrice.text = "200"
        binding.tvDelivary.text = "100"
       binding.tvDiscount.text = "20"
        binding.tvTotal.text = "320"
    }

    private fun showSelectedAddress() {
        binding.run {
            mainStateOrderViewModel.run {
               address.text= selectedAddress.toString()
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






    private fun showDiscount(value: String? = null): Double? {
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



