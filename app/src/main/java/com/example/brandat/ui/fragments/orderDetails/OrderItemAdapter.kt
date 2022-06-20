package com.example.brandat.ui.fragments.orderDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.ItemsInOrderBinding
import com.example.brandat.models.orderModel.LineItem


class OrderItemAdapter(var itemList:List<LineItem>?):RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        var view =  ItemsInOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {

        var orderItem = itemList?.get(position)
       // holder.view.orderImage.setImageResource(orderItem.itemImage)
        holder.view.numberOfItem.text = orderItem?.quantity.toString()
        holder.view.itemPrice.text = orderItem?.price
        holder.view.itemName.text = orderItem?.name

    }

    override fun getItemCount(): Int {
        return  itemList!!.size

    }


    class OrderItemViewHolder(var view : ItemsInOrderBinding): RecyclerView.ViewHolder(view.root){

    }

}