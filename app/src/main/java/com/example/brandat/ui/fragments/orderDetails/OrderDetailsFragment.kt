package com.example.brandat.ui.fragments.orderDetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brandat.R
import com.example.brandat.databinding.FragmentOrderDetailsBinding
import com.example.brandat.databinding.ItemsInOrderBinding
import com.example.brandat.models.orderModel.Order
import com.example.brandat.ui.fragments.myOrder.MyOrderAdapter
import com.example.brandat.ui.fragments.myOrder.OrderModel


class OrderDetailsFragment : Fragment() {
    lateinit var binding: FragmentOrderDetailsBinding
    lateinit var itemAdapter: OrderItemAdapter
    private val args by navArgs<OrderDetailsFragmentArgs>()
    lateinit var order:Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



//        val args: Bundle = requireArguments()
//        val brandId = args.getLong("productId")
//        Log.d("TAG", "onCreateView ppppppp: ${brandId}")
//        Toast.makeText(requireContext(), "productId $brandId", Toast.LENGTH_SHORT).show()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    private fun initRecycler(order:Order?) {
        if(order?.items?.isNotEmpty() == true){
            itemAdapter = OrderItemAdapter(order?.items)
            binding.myOrderRecycler.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                //layoutManager = GridLayoutManager(context,2)
                adapter = itemAdapter
            }
        }

    }

    private fun fakeData(): ArrayList<OrderItemModel> {
        var myOrderList = ArrayList<OrderItemModel>()
        myOrderList.add(OrderItemModel(R.drawable.t_shirt_image3, "shoes", 3, "500$"))
        myOrderList.add(OrderItemModel(R.drawable.shose_image3, "shoes", 3, "500$"))
        myOrderList.add(OrderItemModel(R.drawable.t_shirt_image4, "shoes", 3, "500$"))
        myOrderList.add(OrderItemModel(R.drawable.shose_image3, "shoes", 3, "500$"))
        myOrderList.add(OrderItemModel(R.drawable.t_shirt_image3, "shoes", 3, "500$"))
        myOrderList.add(OrderItemModel(R.drawable.shose_image3, "shoes", 3, "500$"))
        myOrderList.add(OrderItemModel(R.drawable.shose_image3, "shoes", 3, "500$"))
        myOrderList.add(OrderItemModel(R.drawable.shose_image3, "shoes", 3, "500$"))


        return myOrderList
    }

    fun showData(){
        binding.orderNumber.text = order.orderNumber.toString()
        binding.paymentMethod.text = order.gateway
        var date = order.createdAt?.substring(0,10)
        binding.orderDate.text = date
        binding.customerPhone.text = order.finalPrice

    }
}