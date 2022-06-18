package com.example.brandat.ui.fragments.orderStatus.secondState

import android.os.Bundle
import android.provider.SyncStateContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brandat.R
import com.example.brandat.databinding.SecondOrderStateBinding
import com.example.brandat.ui.OrderStatus
import com.example.brandat.ui.fragments.orderStatus.IChangeOrderStatus
import com.example.brandat.ui.fragments.orderStatus.checkOutOrder.CheckOutOrderViewModel
import com.example.brandat.utils.Constants
import java.util.*
import kotlin.concurrent.schedule


class SecondOrderStatus: Fragment() {
    lateinit var binding : SecondOrderStateBinding
    lateinit var iChangeOrderStatus: IChangeOrderStatus
    private var checkout = ""
    private val shardOrderStatusViewModel:CheckOutOrderViewModel by viewModels()


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

        binding.layoutCash.setOnClickListener {
            binding.rdbCash.isChecked = true
            binding.rdbCredit.isChecked = false
            binding.rdbPay.isChecked = false
            shardOrderStatusViewModel.selectedPaymentMethods ="cash"
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
            shardOrderStatusViewModel.selectedPaymentMethods ="paypal"

        }

        binding.btnApply.setOnClickListener {
            if(binding.edtCopon.text.toString().isNotEmpty()) {
                it.visibility = View.GONE
                binding.progress.visibility = View.VISIBLE

                Timer("SettingUp", false).schedule(3000) {
                    requireActivity().runOnUiThread {
                        binding.layoutCoponDone.visibility = View.VISIBLE
                        binding.layoutCopon.visibility = View.GONE
                    }
                }
            }
        }
        binding.btnNext.setOnClickListener {
            if (shardOrderStatusViewModel.selectedPaymentMethods.isEmpty()){
                Toast.makeText(requireContext(), "please select payment Method to continue", Toast.LENGTH_SHORT).show()
            }
            else{
                findNavController().navigate(R.id.action_secondOrderStatus_to_finishOrderStateFragment)
            }
        }
    }
    private fun isCorrectCoupon(code:String):Boolean{
          Constants.discountList?.filter { it.title == code
             return true
         }
       return  false
    }
}