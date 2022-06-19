package com.example.brandat.ui.fragments.orderStatus.secondState

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brandat.R
import com.example.brandat.databinding.SecondOrderStateBinding
import com.example.brandat.models.CustomerAddress
import com.example.brandat.models.orderModel.ShippingAddress
import com.example.brandat.ui.OrderStatus
import com.example.brandat.ui.fragments.orderStatus.IChangeOrderStatus
import com.example.brandat.ui.fragments.orderStatus.checkOutOrder.CheckOutOrderViewModel
import com.example.brandat.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.concurrent.schedule

@AndroidEntryPoint
class SecondOrderStatus: Fragment() {
    lateinit var binding : SecondOrderStateBinding
    lateinit var iChangeOrderStatus: IChangeOrderStatus
    private var checkout = ""
    private val shardOrderStatusViewModel:CheckOutOrderViewModel by viewModels()
    lateinit var selectedAddress: CustomerAddress
     var  selectPaymentMethod:String?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = LayoutInflater.from(container?.context).inflate(R.layout.second_order_state, null)
        binding = SecondOrderStateBinding.bind(view)

        iChangeOrderStatus = requireActivity() as OrderStatus

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            selectedAddress = arguments?.getParcelable<CustomerAddress>("address")!!
            Log.e(ContentValues.TAG, "onViewCreatedthree:${arguments?.getParcelable<CustomerAddress>("address")!!.address1}" )


        }

        binding.layoutCash.setOnClickListener {
            binding.rdbCash.isChecked = true
            binding.rdbCredit.isChecked = false
            binding.rdbPay.isChecked = false
            selectPaymentMethod ="cash"
        }

        binding.layoutCreditCard.setOnClickListener {
            binding.rdbCash.isChecked = false
            binding.rdbCredit.isChecked = true
            binding.rdbPay.isChecked = false
            shardOrderStatusViewModel.selectedPaymentMethods ="paypal"

        }

        binding.layoutPaypal.setOnClickListener {
            binding.rdbCash.isChecked = false
            binding.rdbCredit.isChecked = false
            binding.rdbPay.isChecked = true
            selectPaymentMethod ="paypal"

        }

        binding.btnApply.setOnClickListener {
            if(binding.edtCopon.text.toString().isNotEmpty()) {
                it.visibility = View.GONE
                binding.progress.visibility = View.VISIBLE

                Timer("SettingUp", false).schedule(3000) {
                    requireActivity().runOnUiThread {
                        if (isCorrectCoupon(binding.edtCopon.text.toString())) {
                            binding.layoutCoponDone.visibility = View.VISIBLE
                            binding.layoutCopon.visibility = View.GONE
                        }
                        else{
                            it.visibility = View.VISIBLE
                            binding.progress.visibility = View.GONE
                            Toast.makeText(requireContext(), "invalid code", Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
        }
        binding.btnNext.setOnClickListener {
            if (selectPaymentMethod== null){
                Toast.makeText(requireContext(), "please select payment Method to continue", Toast.LENGTH_SHORT).show()
            }
            else {
                var bundle = Bundle()
                bundle.putParcelable("address", selectedAddress)
                bundle.putString("paymentMethod", selectPaymentMethod)
                findNavController().navigate(R.id.action_secondOrderStatus_to_finishOrderStateFragment, bundle)
                }
            }
        }

    private fun isCorrectCoupon(code:String):Boolean{
        val value = Constants.discounCde?.filter { it.title ==code }
        return value != null
    }
    }

