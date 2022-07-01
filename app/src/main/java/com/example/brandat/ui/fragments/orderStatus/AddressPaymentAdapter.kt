package com.example.brandat.ui.fragments.orderStatus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.RadioButtonItemBinding
import com.example.brandat.models.CustomerAddress

class AddressPaymentAdapter(var onClickListener: OnRadioClickListener)
    : RecyclerView.Adapter<AddressPaymentAdapter.AddressPaymentViewHolder>() {

    private var addresses = emptyList<CustomerAddress>()
    private var selectedPosition = -1
    private var isNewRadioButtonChecked = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddressPaymentViewHolder {
        val view =RadioButtonItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddressPaymentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddressPaymentViewHolder, position: Int) {
        val addressItem = addresses[position]
        holder.view.radioBtn.text = addressItem.address1 .plus(", "+addressItem.city).plus(", "+addressItem.country)
        if (isNewRadioButtonChecked){
            holder.view.radioBtn.isChecked = position == selectedPosition
        }else if (holder.adapterPosition == 0){
                holder.view.radioBtn.isChecked = true
            selectedPosition = 0
        }

        holder.view.radioBtn.setOnClickListener {
            handleRadioButtonChecked(holder.bindingAdapterPosition)
            onClickListener.onItemClick(addressItem)
        }

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int = addresses.size

    fun setDatat(addressList:List<CustomerAddress>){
        addresses = addressList
        notifyDataSetChanged()
    }

    fun handleRadioButtonChecked(adapterPosition:Int){
        isNewRadioButtonChecked = true
        addresses[selectedPosition].isSelected =false
        addresses[adapterPosition].isSelected = true
        selectedPosition = adapterPosition
        notifyDataSetChanged()
    }

    class AddressPaymentViewHolder(var view : RadioButtonItemBinding):RecyclerView.ViewHolder(view.root)
}