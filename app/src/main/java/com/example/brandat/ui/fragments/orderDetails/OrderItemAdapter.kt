package com.example.brandat.ui.fragments.orderDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brandat.databinding.ItemsInOrderBinding
import com.example.brandat.models.orderModel.LineItem

class OrderItemAdapter(var context: Context, var itemList:List<LineItem>?):RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        val view =  ItemsInOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {

        val orderItem = itemList?.get(position)
        holder.view.numberOfItem.text = orderItem?.quantity.toString().plus("  item(s)")
        holder.view.itemPrice.text = orderItem?.price
        holder.view.itemName.text = orderItem?.name?.lowercase()
        Glide.with(context).load(orderItem?.sku).into(holder.view.orderImage)
    }

    override fun getItemCount(): Int {
        return  itemList!!.size
    }

    class OrderItemViewHolder(var view : ItemsInOrderBinding): RecyclerView.ViewHolder(view.root)
}