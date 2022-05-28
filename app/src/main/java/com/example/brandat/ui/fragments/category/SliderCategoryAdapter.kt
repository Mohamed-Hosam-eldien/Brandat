package com.example.brandat.ui.fragments.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.brandat.R
import com.example.brandat.databinding.FragmentCategoryBinding
import com.example.brandat.databinding.SliderCategoryItemBinding

class SliderCategoryAdapter(private val sliderList: ArrayList<CategoryModel>?,
                            private var viewPager: ViewPager2? = null) :
    RecyclerView.Adapter<SliderCategoryAdapter.SliderCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderCategoryViewHolder {
       return  (SliderCategoryViewHolder(SliderCategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)))

    }

    override fun onBindViewHolder(holder: SliderCategoryViewHolder, position: Int) {
//        Glide.with(this)
//            .load(sliderList!![position].categoryImage)
//            .into(holder.imageView)
        holder.binding.imgCategory.setImageResource( sliderList!![position].categoryImage)
        holder.binding.tvCategoryName.text= sliderList!![position].categoryName

    }

    override fun getItemCount(): Int {
        return sliderList!!.size
    }
//===============================================
   class SliderCategoryViewHolder(val binding: SliderCategoryItemBinding): RecyclerView.ViewHolder(binding.root) {
   }
}
