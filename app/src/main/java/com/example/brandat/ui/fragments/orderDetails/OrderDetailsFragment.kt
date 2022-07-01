package com.example.brandat.ui.fragments.orderDetails

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brandat.R
import com.example.brandat.databinding.FragmentOrderDetailsBinding
import com.example.brandat.models.orderModel.Order


class OrderDetailsFragment : Fragment() {
    lateinit var binding: FragmentOrderDetailsBinding
    lateinit var itemAdapter: OrderItemAdapter
    private val args by navArgs<OrderDetailsFragmentArgs>()
    lateinit var order: Order


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_order_details, container, false)

        order = args.currentOrder

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler(order)
        showData()
    }

    private fun initRecycler(order: Order?) {
        if (order?.items?.isNotEmpty() == true) {
            itemAdapter = OrderItemAdapter(requireContext(), order.items)
            binding.myOrderRecycler.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = itemAdapter
            }
        }

    }


    @SuppressLint("SetTextI18n")
    fun showData() {
        binding.orderNumber.text = order.orderNumber.toString()
        val date = order.createdAt?.substring(0, 10)
        binding.orderDate.text = date
        binding.priceValue.text = order.finalPrice
        binding.priceCurrency.text = order.currency
        binding.customerAddress.text =
            order.sourceName[0].uppercaseChar() + order.sourceName.substring(1)
        binding.paymentMethod.text = order.gateway[0].uppercaseChar() + order.gateway.substring(1)
    }
}