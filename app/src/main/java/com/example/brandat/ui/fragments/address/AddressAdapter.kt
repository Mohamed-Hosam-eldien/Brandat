package com.example.brandat.ui.fragments.address

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.AdderssItemBinding
import com.example.brandat.models.CustomerAddress

class AddressAdapter (var onClickListener: OnClickListener)
    : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    var addresses = emptyList<CustomerAddress>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view =AdderssItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddressViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val addressItem = addresses[position]
        holder.view.textAddress.text = addressItem.address1 .plus(", "+addressItem.city).plus(", "+addressItem.country)
        holder.view.rootView.setOnClickListener {
            onClickListener.onItemClick(addressItem)
        }
        holder.view.deleteAddress.setOnClickListener {
             onClickListener.onClick(addressItem)
        }
    }

    override fun getItemCount(): Int {
        return addresses.size

    }

    class  AddressViewHolder(var view : AdderssItemBinding):RecyclerView.ViewHolder(view.root){

    }

    fun setDatat(addressList:List<CustomerAddress>){
        addresses = addressList
        notifyDataSetChanged()
    }
}