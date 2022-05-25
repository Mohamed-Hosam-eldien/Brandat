package com.example.brandat.ui.fragments.orderDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.ItemsInOrderBinding
import com.example.brandat.databinding.MyOrderItemBinding

class OrderItemAdapter(var itemList:ArrayList<OrderItemModel>):RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        var view =  ItemsInOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {

        var orderItem = itemList[position]
        holder.view.orderImage.setImageResource(orderItem.itemImage)
        holder.view.numberOfItem.text= orderItem.itemNumber.toString()
        holder.view.itemPrice.text=orderItem.itemPrice

    }

    override fun getItemCount(): Int {
        return  itemList.size

    }


    class OrderItemViewHolder(var view : ItemsInOrderBinding): RecyclerView.ViewHolder(view.root){

    }

}