package com.example.brandat.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.RecyclerHomeItemBinding

class BrandAdapter(private var brandList :ArrayList<BrandModel>) : RecyclerView.Adapter<BrandAdapter.BrandViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {

        var  binding= RecyclerHomeItemBinding.inflate(LayoutInflater
            .from(parent.context),parent,false)
        return BrandViewHolder(binding)


    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
         var brandItem=brandList[position]
         holder.view.brandName.text=brandItem.brandName
         holder.view.imgBrand.setImageResource(brandItem.brandImage)
        holder.view.brandLayout.setOnClickListener {
            // when click on brand
        }
    }

    override fun getItemCount(): Int {
        return brandList.size
    }




    class BrandViewHolder(var view : RecyclerHomeItemBinding):RecyclerView.ViewHolder(view.root){

    }
}