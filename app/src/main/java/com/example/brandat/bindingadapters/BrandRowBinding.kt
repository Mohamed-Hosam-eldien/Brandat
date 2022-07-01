package com.example.brandat.bindingadapters

import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.brandat.R
import com.example.brandat.ui.fragments.newcategory.ProductModel

class BrandRowBinding {
    companion object {
        @JvmStatic
        @BindingAdapter("setImagForBrand")
        fun setImagForBrand(imageView: ImageView, imageUrl: String) {
            Glide.with(imageView.context).load(imageUrl).into(imageView)
        }


        @BindingAdapter("onBrandClicked")
        @JvmStatic
        fun onBrandClicked(productCard: CardView, product: ProductModel) {
            productCard.setOnClickListener {

               val navController = Navigation.findNavController(productCard)
                navController.navigate(R.id.action_homeFragment_to_newCategoryFragment)
            }
        }
    }
}