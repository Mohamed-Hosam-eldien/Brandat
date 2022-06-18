package com.example.brandat.ui.fragments.myOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brandat.R
import com.example.brandat.databinding.FragmentMyOrderBinding
import com.example.brandat.models.orderModel.Order
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOrderFragment : Fragment(), OnItemClickLinter {

    private val TAG: String = "Main"
    lateinit var binding: FragmentMyOrderBinding
    //lateinit var myOrdrAdapter: MyOrderAdapter
    lateinit var navController: NavController
    private val viewModel: MyOrderViewModel by viewModels()
    private val myOrderAdapter by lazy { MyOrderAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_order, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        initRecycler()
        showObservedData()
    }

    private fun initRecycler() {

        binding.myOrderRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            adapter = myOrderAdapter
        }
    }

    private fun fakeData(): ArrayList<OrderModel> {
        var myOrderList = ArrayList<OrderModel>()
        for (i in 1..10) {
            myOrderList.add(
                OrderModel(
                    "shoes",
                    "cairo",
                    "Active",
                    "2000$",
                    "2-2-2020",
                    R.drawable.shose_image3,
                    "5 item"
                )
            )
        }


        return myOrderList
    }

    fun showObservedData() {
        viewModel.getOrdersFromApi()
        viewModel.getOrder.observe(viewLifecycleOwner) {

                Toast.makeText(context, "${it.body()}", Toast.LENGTH_SHORT).show()
                initView(it.body()!!.orders)

        }

    }

    private fun initView(order: List<Order>) {
        myOrderAdapter.setDatat(order)
        //addressAdapter = AddressAdapter(this)
        binding.myOrderRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = myOrderAdapter
        }

    }


    override fun onClick(orderItem: Order) {
        findNavController().navigate(R.id.action_orderFragment_to_orderDetailsFragment)
    }

}
