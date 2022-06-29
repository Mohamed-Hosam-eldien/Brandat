package com.example.brandat.ui.fragments.myOrder

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.MyOrderItemBinding
import com.example.brandat.models.orderModel.Order
import com.example.brandat.utils.Constants
import com.example.brandat.utils.convertCurrency


class MyOrderAdapter(var onItemClickLinter: OnItemClickLinter, val context: Context) :
    RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder>() {

    var orders = emptyList<Order>()

    var currency: String = "EGP"

    init {
        val sharedPreferences =
            context.getSharedPreferences(Constants.SHARD_NAME, Context.MODE_PRIVATE)
        currency = sharedPreferences.getString(Constants.CURRENCY_TYPE, "EGP")!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrderViewHolder {
        val view = MyOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyOrderViewHolder, position: Int) {

        val orderItem = orders[position]
        val date = orderItem.createdAt?.substring(0, 10)
        holder.view.orderDate.text = date

        holder.view.price.text = " ".plus(convertCurrency(orderItem.finalPrice?.toDouble(), holder.itemView.context))
            .plus(" ").plus(currency)
//        holder.view.itemsNumber.text=orderItem.itemsNumber
        holder.view.orderStatus.text = orderItem.financialStatus
        holder.view.cardView.setOnClickListener {
            onItemClickLinter.onClick(orderItem)
        }
    }

    override fun getItemCount(): Int {
        return orders.size

    }


    fun setDatat(OrderList: List<Order>) {
        orders = OrderList
        notifyDataSetChanged()
    }

    class MyOrderViewHolder(var view: MyOrderItemBinding) : RecyclerView.ViewHolder(view.root) {

    }

}