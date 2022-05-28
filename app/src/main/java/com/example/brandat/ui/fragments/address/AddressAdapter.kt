package com.example.brandat.ui.fragments.address

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.AdderssItemBinding

class AddressAdapter (var addressList:ArrayList<AddressModel>,var onClickListener: OnClickListener) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        var view =AdderssItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddressViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        var addressItem = addressList[position]
        holder.view.textAddress.text = addressItem.countryName .plus(addressItem.Governorate).plus(addressItem.street)
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