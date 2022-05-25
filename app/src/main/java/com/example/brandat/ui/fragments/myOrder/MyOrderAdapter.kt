package com.example.brandat.ui.fragments.myOrder

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.MyOrderItemBinding

class MyOrderAdapter(var myOrderList:ArrayList<OrderModel>,var onItemClickLinter: OnItemClickLinter) :RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrderViewHolder {
        var view =  MyOrderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyOrderViewHolder, position: Int) {

      var orderItem = myOrderList[position]
       holder.view.orderDate.text=orderItem.orderDate
        holder.view.price.text=orderItem.orderTotalPrice
//        holder.view.itemsNumber.text=orderItem.itemsNumber
        holder.view.orderStatus.text=orderItem.orderStatus
        holder.view.cardView.setOnClickListener {
            Log.e(TAG, "onBindViewHolder: ", )
                 onItemClickLinter.onClick(holder.view.cardView,orderItem)
        }
    }

    override fun getItemCount(): Int {
      return  myOrderList.size

    }


    class MyOrderViewHolder(var view :MyOrderItemBinding):RecyclerView.ViewHolder(view.root){

    }

}