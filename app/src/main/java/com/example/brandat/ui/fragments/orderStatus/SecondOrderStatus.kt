package com.example.brandat.ui.fragments.orderStatus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.brandat.R
import com.example.brandat.databinding.SecondOrderStateBinding
import com.example.brandat.ui.OrderStatus
import java.util.*
import kotlin.concurrent.schedule


class SecondOrderStatus: Fragment() {

    lateinit var binding : SecondOrderStateBinding
    lateinit var iChangeOrderStatus:IChangeOrderStatus
    private var checkout = ""


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
            checkout = "cash"
        }

        binding.layoutCreditCard.setOnClickListener {
            binding.rdbCash.isChecked = false
            binding.rdbCredit.isChecked = true
            binding.rdbPay.isChecked = false
            checkout = "credit"
        }

        binding.layoutPaypal.setOnClickListener {
            binding.rdbCash.isChecked = false
            binding.rdbCredit.isChecked = false
            binding.rdbPay.isChecked = true
            checkout = "paypal"
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
    }

}