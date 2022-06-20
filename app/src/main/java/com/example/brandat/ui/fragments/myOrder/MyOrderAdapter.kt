package com.example.brandat.ui.fragments.myOrder

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.MyOrderItemBinding
import com.example.brandat.test.Order

class MyOrderAdapter(var onItemClickLinter: OnItemClickLinter) :
    RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder>() {

    var orders = emptyList<com.example.brandat.ordermodel.Order>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrderViewHolder {
        var view = MyOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyOrderViewHolder, position: Int) {

        var orderItem = orders[position]
        var date = orderItem.createdAt?.substring(0, 10)
        holder.view.orderDate.text = date
        holder.view.price.text = orderItem.finalPrice
//        holder.view.itemsNumber.text=orderItem.itemsNumber
        holder.view.orderStatus.text = orderItem.financialStatus
        holder.view.cardView.setOnClickListener {
            Log.e(TAG, "onBindViewHolder: ")
            onItemClickLinter.onClick(orderItem)
        }
    }

    override fun getItemCount(): Int {
        return orders.size

    }


    fun setDatat(OrderList: List<com.example.brandat.ordermodel.Order>) {
        orders = OrderList
        notifyDataSetChanged()
    }

    class MyOrderViewHolder(var view: MyOrderItemBinding) : RecyclerView.ViewHolder(view.root) {

    }

}