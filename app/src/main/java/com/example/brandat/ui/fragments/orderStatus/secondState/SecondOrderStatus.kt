package com.example.brandat.ui.fragments.orderStatus.secondState

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.brandat.R
import com.example.brandat.databinding.SecondOrderStateBinding
import com.example.brandat.models.CustomerAddress
import com.example.brandat.ui.OrderStatus
import com.example.brandat.ui.fragments.orderStatus.IChangeOrderStatus
import com.example.brandat.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.concurrent.schedule


@AndroidEntryPoint
class SecondOrderStatus: Fragment() {
    lateinit var binding: SecondOrderStateBinding
    lateinit var iChangeOrderStatus: IChangeOrderStatus
    private var discount = 0.0
    private  var price =0.0
    lateinit var currencyCode:String
    lateinit var sharedPreferences : SharedPreferences
    lateinit var selectedAddress: CustomerAddress
    var selectPaymentMethod: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view =
            LayoutInflater.from(container?.context).inflate(R.layout.second_order_state, null)
        binding = SecondOrderStateBinding.bind(view)

        iChangeOrderStatus = requireActivity() as OrderStatus

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences =requireActivity().getSharedPreferences(Constants.SHARD_NAME, Context.MODE_PRIVATE)
        currencyCode = sharedPreferences.getString(Constants.CURRENCY_TYPE,getString(R.string.egypt_currency))!!
        price= Constants.totalPrice?:0.0
        initView()
        arguments?.let {
            selectedAddress = arguments?.getParcelable("address")!!
        }

        binding.layoutCash.setOnClickListener {
            if (price<= 400.00) {
                binding.rdbCash.isChecked = true
                binding.rdbPay.isChecked = false
                selectPaymentMethod = "cash"

            }
            else{
                showAlertDialog()
            }
        }


        binding.layoutPaypal.setOnClickListener {
            binding.rdbCash.isChecked = false
            binding.rdbPay.isChecked = true
            selectPaymentMethod = "paypal"

        }

        binding.btnApply.setOnClickListener {
            if (binding.edtCopon.text.toString().isNotEmpty()) {
                if(!Constants.getDiscount){
                it.visibility = View.GONE
                binding.progress.visibility = View.VISIBLE

                Timer("SettingUp", false).schedule(3000) {
                    requireActivity().runOnUiThread {
                        val discountValue = isCorrectCoupon(binding.edtCopon.text.toString())
                        if (discountValue == "") {
                            it.visibility = View.VISIBLE
                            binding.progress.visibility = View.GONE
                            binding.btnApply.text =getString(R.string.retry)
                            Toast.makeText(requireContext(), "invalid code", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            calacDiscount(discountValue ?:"0.0")
                            Constants.getDiscount = true
                            binding.layoutCoponDone.visibility = View.VISIBLE
                            binding.layoutCopon.visibility = View.GONE
                        }
                    }
                }
                }
                else{
                    Toast.makeText(requireContext(), "you take your discount before", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.btnNext.setOnClickListener {
            if (selectPaymentMethod == null) {
                Toast.makeText(
                    requireContext(),
                    "please select payment Method to continue",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val bundle = Bundle()
                bundle.putParcelable("address", selectedAddress)
                bundle.putString("paymentMethod", selectPaymentMethod)
                bundle.putDouble("discount",discount)
                bundle.putDouble("price",price)
                findNavController().navigate(R.id.action_secondOrderStatus_to_finishOrderStateFragment, bundle)
                iChangeOrderStatus.changeStatus(2)

            }
        }
    }

    private fun initView() {
        binding.subPrice.text = Constants.totalPrice?.toString()?.plus(" ")?.plus(currencyCode)?: "0.0"
        binding.totalPrice.text = price.minus(discount).toString().plus(" ").plus(currencyCode)
        binding.discount.text = discount.toString().plus(" ").plus(currencyCode)

    }

    private fun isCorrectCoupon(code: String): String? {
        val value = Constants.discounCde?.filter { it.title == code }
        if (value?.size!=0) {
            return value?.get(0)?.value
        }
        return ""
    }

    private fun calacDiscount(discountValue: String) {
        price.let {
            discount = (it * (discountValue.toDouble()*-1) / 100)
            price = it - discount
            binding.discount.text=discount.toString().plus(" ").plus(currencyCode)
            binding.totalPrice.text = price.toString().plus(" ").plus(currencyCode)
        }

    }

    private fun showAlertDialog() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.ok)) { _, _ ->

        }

        builder.setIcon(R.drawable.ic_warning)
        builder.setTitle(getString(R.string.warning))
        builder.setMessage(getString(R.string.warning_message))
        builder.create().show()
    }
}

