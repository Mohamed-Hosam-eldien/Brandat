package com.example.brandat.ui.fragments.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brandat.databinding.RecyclerHomeItemBinding
import com.example.brandat.models.Brand
import com.example.brandat.utils.BrandDiffUtil

class BrandAdapter(var context: Context, var clickListner: BrandOnClickListner) :
    RecyclerView.Adapter<BrandAdapter.BrandViewHolder>() {
    private var brandList = ArrayList<Brand>()

    fun setData(newData: List<Brand>) {
        val brandDiffutil = BrandDiffUtil(brandList, newData)
        val productDiffUtilResult = DiffUtil.calculateDiff(brandDiffutil)
        brandList = ArrayList(newData)
        productDiffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {

        val binding =
            RecyclerHomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BrandViewHolder(binding)


    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {

        val brandItem = brandList[position]
        holder.view.brandName.text = brandItem.title

        Glide.with(context).load(brandItem.image.src).into(holder.view.imgBrand)
        holder.itemView.setOnClickListener {
            clickListner.onBrandClick(brandItem.title)
        }

    }

    override fun getItemCount(): Int {
        return brandList.size
    }

    class BrandViewHolder(var view: RecyclerHomeItemBinding) : RecyclerView.ViewHolder(view.root)
}