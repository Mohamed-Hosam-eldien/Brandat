package com.example.brandat.ui.fragments.address

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.AdderssItemBinding
import com.example.brandat.models.CustomerAddress

class AddressAdapter (var addressList:List<CustomerAddress>,var onClickListener: OnClickListener)
    : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        var view =AdderssItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddressViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        var addressItem = addressList[position]
        holder.view.textAddress.text = addressItem.address1 .plus(addressItem.city).plus(addressItem.country)
        holder.view.deleteAddress.setOnClickListener {
             onClickListener.onClick()
        }
    }

    override fun getItemCount(): Int {
        return addressList.size

    }

    class  AddressViewHolder(var view : AdderssItemBinding):RecyclerView.ViewHolder(view.root){

    }
}