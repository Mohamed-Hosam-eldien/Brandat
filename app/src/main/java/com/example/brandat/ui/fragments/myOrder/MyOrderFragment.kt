package com.example.brandat.ui.fragments.myOrder

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brandat.R
import com.example.brandat.databinding.FragmentMyOrderBinding


class MyOrderFragment : Fragment(),OnItemClickLinter {

        lateinit var binding : FragmentMyOrderBinding
        lateinit var  myOrdrAdapter :MyOrderAdapter
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_order,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)

        initRecycler()
    }

      private  fun initRecycler(){
         myOrdrAdapter = MyOrderAdapter(fakeData(),this)
         binding.myOrderRecycler.apply {
                 layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

             adapter = myOrdrAdapter
         }
      }
    private fun fakeData() :ArrayList<OrderModel>{
        var myOrderList = ArrayList<OrderModel>()
        for (i in 1 ..10){
        myOrderList.add(OrderModel("shoes","cairo","Active","2000$","2-2-2020",R.drawable.shose_image3,"5 item"))
}


        return  myOrderList
    }

    companion object {

            }

    override fun onClick(orderItem1: View, orderItem: OrderModel) {
        Log.e(ContentValues.TAG, "dddd: ", )
            //navController.navigate(R.id.action_orderFragment_to_orderDetailsFragment)
       orderItem1.findNavController().navigate(R.id.action_orderFragment_to_orderDetailsFragment)

//        var actoion=HomeFragmentDirections.actionHomeFragmentToDayDetails(orderItem)
//        findNavController().navigate(actoion)
    }

}
